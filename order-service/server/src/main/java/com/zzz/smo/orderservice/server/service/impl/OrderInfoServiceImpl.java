package com.zzz.smo.orderservice.server.service.impl;

import com.google.gson.Gson;

import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.common.utils.ResultVOUtil;
import com.zzz.smo.orderservice.server.dataobject.CommodityInfoAndStock;
import com.zzz.smo.orderservice.server.dataobject.StockOutput;
import com.zzz.smo.orderservice.server.dto.OrderDTO;
import com.zzz.smo.orderservice.server.dto.StockDTO;
import com.zzz.smo.orderservice.server.entity.OrderDetail;
import com.zzz.smo.orderservice.server.entity.OrderInfo;
import com.zzz.smo.orderservice.server.feignclient.CommodityClient;
import com.zzz.smo.orderservice.server.message.Order2CommoditySender;
import com.zzz.smo.orderservice.server.repository.OrderInfoRepository;
import com.zzz.smo.orderservice.server.service.OrderDetailService;
import com.zzz.smo.orderservice.server.service.OrderInfoService;
import com.zzz.smo.orderservice.server.util.OrderInfoIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Sean
 * @Date: 2019/1/6 14:18
 */
@Service
@Slf4j
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CommodityClient commodityClient;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private Order2CommoditySender order2CommoditySender;

    /**
     * 获取商品信息和库存
     * 计算总价
     * 扣减库存
     * 创建订单
     * @param orderDTO
     * @return
     */
    //创建订单的同时扣库存
    //调用商品服务计算总价格
    @Transactional
    @Override
    public ResultVO createAnOrder(OrderDTO orderDTO) {
        OrderInfo orderInfo = new OrderInfo();
        orderDTO.setCreateDate(new Date());
        //生成id
        orderDTO.setId(new OrderInfoIdWorker().idWorker());
        BeanUtils.copyProperties(orderDTO,orderInfo);


        //获得商品的具体信息和库存
        ResultVO infoResult = getCommodityInfoAndStock(orderDTO);
        //System.out.println("测试商品价格"+orderDTO.getDetails().get(0).getPrice());
        if (infoResult.getCode()==1){
            return infoResult;
        }


        //计算总价格
        calculateAmount(orderDTO);
        System.out.println("得到的总价格为："+orderDTO.getTotalAmount());
        orderInfo.setTotalAmount(orderDTO.getTotalAmount());

        //扣减库存
        List<OrderDetail> detailList = orderDTO.getDetails();
        List<StockDTO> stockDTOList = new ArrayList<>();
        for (OrderDetail o :
                detailList) {
            stockDTOList.add(new StockDTO(o.getCommodityInfoId(), o.getNums()));
        }
        //异步扣库存
        StockOutput stockOutput = new StockOutput();
        stockOutput.setOrderId(orderDTO.getId());
        stockOutput.setStockDTOList(stockDTOList);
        order2CommoditySender.decreaseStock(stockOutput);
        //amqpTemplate.convertAndSend("myExchange","decreaseStock",new Gson().toJson(stockDTOList));
//        ResultVO stockResultVO = commodityClient.reduceStock(stockDTOList);
//        if (stockResultVO.getCode()==0){
        //异步创建订单详细信息
        amqpTemplate.convertAndSend("orderExchange","createOrder",new Gson().toJson(orderDTO));
//        orderDetailService.createDetails(orderDTO);
//        orderInfoRepository.save(orderInfo);
        log.info("新写入订单，订单的id为： "+orderInfo.getId());
        return ResultVOUtil.success(orderDTO);
//        }
//        else
//            return null;

    }

    //获取商品信息和库存信息
    private ResultVO getCommodityInfoAndStock(OrderDTO orderDTO){
        //组成数组
        List<OrderDetail> list = orderDTO.getDetails();
        List<Long> commodityInfoIdList = orderDTO.getDetails().stream()
                .map(OrderDetail::getCommodityInfoId).collect(Collectors.toList());
//        List<Long> result = new ArrayList<>();
//        for (OrderDetail o:list
//             ) {
//            result.add(o.getCommodityInfoId());
//        }
        //调用商品服务
        List<CommodityInfoAndStock> commodityInfoList = commodityClient.findOrderList(commodityInfoIdList);
        List<StockDTO> stockDTOList = new ArrayList<>();
        for (CommodityInfoAndStock c:commodityInfoList
             ) {
            for (OrderDetail o:list
            ) {
                if(c.getId() == o.getCommodityInfoId()){
                    //判断库存是否足够
                    if (c.getNums()<o.getNums()){
                        StockDTO stockDTO = new StockDTO();
                        stockDTO.setCommodityInfoId(c.getId());
                        stockDTO.setNums(c.getNums());
                        stockDTOList.add(stockDTO);
                    }
                    else
                        o.setPrice(c.getPrice());
                }
            }
        }
        if (stockDTOList.size()>0)
            return ResultVOUtil.fail(stockDTOList);
        else {
            orderDTO.setDetails(list);
            return ResultVOUtil.success(null);
        }

    }
    //计算总价格,有待改进
    private void calculateAmount(OrderDTO orderDTO){
        BigDecimal sum = new BigDecimal("0");
        List<OrderDetail> detailList = orderDTO.getDetails();
        //有待改进
        for (OrderDetail o :
                detailList) {
            System.out.println("单件价格为："+o.getPrice());
            System.out.println("单件数量为："+o.getNums());
            BigDecimal temp = o.getPrice().multiply(BigDecimal.valueOf(o.getNums()));
            System.out.println("中间值为："+temp);
            sum = sum.add(temp);
        }
        System.out.println("计算总价格为： "+sum);
        orderDTO.setTotalAmount(sum);
    }


    /**
     * 1.获取订单主信息
     * 2.获取订单详情
     * 3.构造DTO返回
     * @param id
     * @return
     */
    @Override
    public OrderDTO findById(long id) {
        OrderInfo orderInfo = orderInfoRepository.getOne(id);
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderInfo,orderDTO);
        List<OrderDetail> detailList = orderDetailService.findByOrderInfoId(id);
        orderDTO.setDetails(detailList);
        return orderDTO;
    }

    @Override
    public List<OrderDTO> findByUsername(String username) {
        //查询概要信息
        List<OrderInfo> orderInfoList = orderInfoRepository.findByUsername(username);
        List<OrderDTO> result = new ArrayList<>();
        for (OrderInfo o :
                orderInfoList) {
            //查询详细信息
            List<OrderDetail> orderDetailList = orderDetailService.findByOrderInfoId(o.getId());
            //构造DTO
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(o,orderDTO);
            orderDTO.setDetails(orderDetailList);
            result.add(orderDTO);
        }


        return result;
    }

    @Override
    public void userCancel(OrderDTO orderDTO) {
        orderInfoRepository.deleteById(orderDTO.getId());

    }

    @Override
    public void changeStatus(OrderDTO orderDTO) {
        OrderInfo orderInfo = orderInfoRepository.getOne(orderDTO.getId());
        orderInfo.setStatus(orderDTO.getStatus());
        orderInfoRepository.save(orderInfo);
    }

    @Override
    public void save(OrderInfo orderInfo) {
        orderInfoRepository.save(orderInfo);
    }

    @Override
    public void deleteOne(long id) {
        orderInfoRepository.deleteById(id);
    }
}
