package com.zzz.smo.orderservice.service;

import com.zzz.smo.orderservice.dto.OrderDTO;
import com.zzz.smo.orderservice.entity.OrderDetail;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/6 14:31
 */
public interface OrderDetailService {
    void createDetails(OrderDTO orderDTO);
    List<OrderDetail> findByOrderInfoId(long orderInfoId);
}
