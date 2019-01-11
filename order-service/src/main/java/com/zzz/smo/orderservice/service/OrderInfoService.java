package com.zzz.smo.orderservice.service;

import com.zzz.smo.orderservice.dto.OrderDTO;
import com.zzz.smo.orderservice.entity.OrderInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/6 14:13
 */
public interface OrderInfoService {
    OrderDTO createAnOrder(OrderDTO orderDTO);
    OrderDTO findById(long id);
    List<OrderDTO> findByUsername(String username);
}
