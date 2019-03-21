package com.zzz.smo.orderservice.server.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.common.utils.ResultVOUtil;
import com.zzz.smo.orderservice.server.converter.OrderForm2OrderDTOConverter;
import com.zzz.smo.orderservice.server.dto.OrderDTO;
import com.zzz.smo.orderservice.server.dto.StockDTO;
import com.zzz.smo.orderservice.server.entity.OrderDetail;
import com.zzz.smo.orderservice.server.enums.OrderStatusEnum;
import com.zzz.smo.orderservice.server.exception.OrderException;
import com.zzz.smo.orderservice.server.feignclient.CommodityClient;
import com.zzz.smo.orderservice.server.form.OrderForm;
import com.zzz.smo.orderservice.server.message.Order2CommoditySender;
import com.zzz.smo.orderservice.server.service.OrderDetailService;
import com.zzz.smo.orderservice.server.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * @Author: Sean
 * @Date: 2019/1/8 21:53
 */
@RestController
@RequestMapping("/order")
@Slf4j
@DefaultProperties(defaultFallback = "defaultFallback")
public class OrderController {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CommodityClient commodityClient;
    @Autowired
    private Order2CommoditySender order2CommoditySender;

    //TODO 增加创建订单后30分钟未支付自动取消订单
    @PostMapping
    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")
    })
    public ResultVO create(@Valid OrderForm orderForm, BindingResult bindingResult){
        //参数校验
        if(bindingResult.hasErrors()){
            log.error("订单参数不正确，orderForm={}",orderForm);
            throw new OrderException(1,bindingResult.getFieldError().getDefaultMessage());
        }
        //orderForm -> orderDTO
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        //判断购买的商品是否为空
        if(CollectionUtils.isEmpty(orderDTO.getDetails())){
            log.error("【创建订单】购买商品信息为空");
            throw new OrderException(1,"购买商品信息为空");
        }
        //创建订单
        ResultVO result = orderInfoService.createAnOrder(orderDTO);
        //构造返回
        if (result.getCode()==1){
            //失败了
            return result;
        }
        Map<String,Long> map = new HashMap<>();
        OrderDTO resultOrderDTO = (OrderDTO) result.getData();
        map.put("orderId",resultOrderDTO.getId());
        return ResultVOUtil.success(map);
    }

    //该接口暂不使用
    @GetMapping("/orders")
    public ResultVO findAll(){

        return null;
    }

    @GetMapping("/{id}")
    public ResultVO findOne(@PathVariable long id){
        OrderDTO orderDTO = orderInfoService.findById(id);
        return ResultVOUtil.success(orderDTO);
    }

    @GetMapping("/user/{username}")
    public ResultVO findByUser(@PathVariable String username){
        ResultVO resultVO = ResultVOUtil.success(orderInfoService.findByUsername(username));
        return resultVO;
    }

    public ResultVO findByStatus(int status){
        return null;
    }

    //用户取消还未被商家处理的订单
    //取消订单后把库存加上去
    @DeleteMapping("/user/cancel")
    public ResultVO userCancel(String username,long orderId){
        //获取订单信息
        OrderDTO orderDTO = orderInfoService.findById(orderId);
        //判断支付状态
        if (orderDTO.getStatus() <= OrderStatusEnum.PAID.getCode()
                && orderDTO.getUsername().equals(username)){
            List<StockDTO> stockDTOList = new ArrayList<>();
            for (OrderDetail orderDtail :
                    orderDTO.getDetails()) {
                StockDTO stockDTO = new StockDTO();
                BeanUtils.copyProperties(orderDtail,stockDTO);
                stockDTOList.add(stockDTO);
            }
            //TODO 异步改造
            order2CommoditySender.increaseStock(stockDTOList);
            //commodityClient.addStock(stockDTOList);
            orderInfoService.userCancel(orderDTO);
            orderDetailService.userCancel(orderDTO.getDetails());
            return ResultVOUtil.success("成功取消订单");
        }
        else
            return ResultVOUtil.fail("取消订单失败，请联系商家");
    }

    //更改订单状态，需要管理员权限
    @PutMapping("/admin/orderStatus")
    public ResultVO changeOrderStatus(long orderId,int orderStatus){
        OrderDTO orderDTO = orderInfoService.findById(orderId);
        orderDTO.setStatus(orderStatus);
        orderInfoService.changeStatus(orderDTO);
        return ResultVOUtil.success("订单状态变更为： "+ orderStatus);
    }

    @DeleteMapping
    public ResultVO deleteOne(long orderId){
        orderInfoService.deleteOne(orderId);
        orderDetailService.deleteByOrderInfoId(orderId);
        return ResultVOUtil.success("订单删除成功");
    }

    //TODO 订单支付

    //feign测试用例
    @GetMapping("/commodity")
    @HystrixCommand(fallbackMethod = "fallback",commandProperties  = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60")})
    public String getCommodity(){

        List list = commodityClient.findOrderList(Arrays.asList(Long.valueOf(11),Long.valueOf(22)));
        log.info("大小为"+list.size());
        return "success"+list.size();
    }

    public String fallback(){
        return "太拥挤了，请稍后再试";
    }

    public ResultVO defaultFallback(){
        return ResultVOUtil.fail("太拥挤了，请稍后再试");
    }
}
