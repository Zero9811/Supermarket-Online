package com.zzz.smo.orderservice.server.service;


import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.orderservice.server.dto.OrderDTO;
import com.zzz.smo.orderservice.server.entity.OrderInfo;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/6 14:13
 */
public interface OrderInfoService {
    ResultVO createAnOrder(OrderDTO orderDTO);
    OrderDTO findById(long id);
    List<OrderDTO> findByUsername(String username);
    void userCancel(OrderDTO orderDTO);
    void changeStatus(OrderDTO orderDTO);
    void save(OrderInfo orderInfo);
    void deleteOne(long id);
}
