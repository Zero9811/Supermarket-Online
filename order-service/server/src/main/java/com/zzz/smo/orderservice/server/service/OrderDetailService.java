package com.zzz.smo.orderservice.server.service;



import com.zzz.smo.orderservice.server.dto.OrderDTO;
import com.zzz.smo.orderservice.server.entity.OrderDetail;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/6 14:31
 */
public interface OrderDetailService {
    void createDetails(OrderDTO orderDTO);
    List<OrderDetail> findByOrderInfoId(long orderInfoId);
    void userCancel(List<OrderDetail> details);
    void deleteByOrderInfoId(long orderInfoId);
}
