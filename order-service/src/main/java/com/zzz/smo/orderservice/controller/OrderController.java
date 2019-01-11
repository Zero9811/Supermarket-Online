package com.zzz.smo.orderservice.controller;

import com.zzz.smo.orderservice.VO.ResultVO;
import com.zzz.smo.orderservice.converter.OrderForm2OrderDTOConverter;
import com.zzz.smo.orderservice.dto.OrderDTO;
import com.zzz.smo.orderservice.exception.OrderException;
import com.zzz.smo.orderservice.feignclient.CommodityClient;
import com.zzz.smo.orderservice.form.OrderForm;
import com.zzz.smo.orderservice.service.OrderDetailService;
import com.zzz.smo.orderservice.service.OrderInfoService;
import com.zzz.smo.orderservice.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Sean
 * @Date: 2019/1/8 21:53
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private CommodityClient commodityClient;

    @PostMapping("/order")
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
        OrderDTO result = orderInfoService.createAnOrder(orderDTO);
        //构造返回
        Map<String,Long> map = new HashMap<>();
        map.put("orderId",result.getId());
        return ResultVOUtil.success(map);
    }

    @GetMapping("/orders")
    public ResultVO findAll(){

        return null;
    }

    @GetMapping("/order")
    public ResultVO findOne(long id){
        OrderDTO orderDTO = orderInfoService.findById(id);
        return ResultVOUtil.success(orderDTO);
    }

    @GetMapping("/user")
    public ResultVO findByUser(String username){
        ResultVO resultVO = ResultVOUtil.success(orderInfoService.findByUsername(username));
        return resultVO;
    }

    //feign测试用例
    @GetMapping("/commodity")
    public String getCommodity(){
        List list = commodityClient.findOrderList(Arrays.asList(Long.valueOf(11),Long.valueOf(22)));
        log.info("大小为"+list.size());
        return "success";
    }
}
