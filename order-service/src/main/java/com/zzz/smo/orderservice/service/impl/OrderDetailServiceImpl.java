package com.zzz.smo.orderservice.service.impl;

import com.zzz.smo.orderservice.dto.OrderDTO;
import com.zzz.smo.orderservice.entity.OrderDetail;
import com.zzz.smo.orderservice.repository.OrderDetailRepository;
import com.zzz.smo.orderservice.service.OrderDetailService;
import com.zzz.smo.orderservice.util.OrderDetailIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/6 14:32
 */
@Service
public class OrderDetailServiceImpl implements OrderDetailService {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Transactional
    @Override
    public void createDetails(OrderDTO orderDTO) {
        List<OrderDetail> detailList = orderDTO.getDetails();
        for (OrderDetail o :
                detailList) {
            o.setOrderInfoId(orderDTO.getId());
            o.setId(new OrderDetailIdWorker().idWorker());
            orderDetailRepository.saveAndFlush(o);
        }
    }

    @Override
    public List<OrderDetail> findByOrderInfoId(long orderInfoId) {
        return orderDetailRepository.findByOrderInfoId(orderInfoId);
    }
}
