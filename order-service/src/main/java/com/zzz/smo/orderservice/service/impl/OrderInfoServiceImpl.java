package com.zzz.smo.orderservice.service.impl;

import com.zzz.smo.orderservice.VO.ResultVO;
import com.zzz.smo.orderservice.dto.OrderDTO;
import com.zzz.smo.orderservice.dto.StockDTO;
import com.zzz.smo.orderservice.entity.CommodityInfo;
import com.zzz.smo.orderservice.entity.OrderDetail;
import com.zzz.smo.orderservice.entity.OrderInfo;
import com.zzz.smo.orderservice.feignclient.CommodityClient;
import com.zzz.smo.orderservice.repository.OrderInfoRepository;
import com.zzz.smo.orderservice.service.OrderDetailService;
import com.zzz.smo.orderservice.service.OrderInfoService;
import com.zzz.smo.orderservice.util.OrderInfoIdWorker;
import lombok.extern.slf4j.Slf4j;
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

    //TODO 创建订单的同时扣库存
    //调用商品服务计算总价格
    @Transactional
    @Override
    public OrderDTO createAnOrder(OrderDTO orderDTO) {
        OrderInfo orderInfo = new OrderInfo();

        orderInfo.setId(new OrderInfoIdWorker().idWorker());
        orderInfo.setUsername(orderDTO.getUsername());
        orderInfo.setStatus(0);
        orderInfo.setCreateDate(new Date());
        orderDTO.setId(orderInfo.getId());

        //获得商品的具体信息
        getCommodityInfo(orderDTO);
        System.out.println("测试商品价格"+orderDTO.getDetails().get(0).getPrice());


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
        ResultVO stockResultVO = commodityClient.reduceStock(stockDTOList);
        if (stockResultVO.getCode()==0){
            //创建订单详细信息
            orderDetailService.createDetails(orderDTO);
            orderInfoRepository.save(orderInfo);
            log.info("新写入订单，订单的id为： "+orderInfo.getId());
            return orderDTO;
        }
        else
            return null;

    }

    //获取商品信息
    private void getCommodityInfo(OrderDTO orderDTO){
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
        List<CommodityInfo> commodityInfoList = commodityClient.findOrderList(commodityInfoIdList);
        for (CommodityInfo c:commodityInfoList
             ) {
            for (OrderDetail o:list
            ) {
                if(c.getId() == o.getCommodityInfoId()){
                    o.setPrice(c.getPrice());
                }
            }
        }
        orderDTO.setDetails(list);
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
}
