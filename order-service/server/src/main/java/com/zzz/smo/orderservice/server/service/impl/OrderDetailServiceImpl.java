package com.zzz.smo.orderservice.server.service.impl;

import com.zzz.smo.orderservice.server.dto.OrderDTO;
import com.zzz.smo.orderservice.server.entity.OrderDetail;
import com.zzz.smo.orderservice.server.repository.OrderDetailRepository;
import com.zzz.smo.orderservice.server.service.OrderDetailService;
import com.zzz.smo.orderservice.server.util.OrderDetailIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

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

    //TODO 待优化，批量删除
    @Override
    public void userCancel(List<OrderDetail> details) {
        List<Long> idList = details.stream().
                map(OrderDetail::getId).collect(Collectors.toList());
        for (Long id :
                idList) {
            //待优化
            orderDetailRepository.deleteById(id);
        }

    }

    @Override
    public void deleteByOrderInfoId(long orderInfoId) {
        orderDetailRepository.deleteByOrderInfoId(orderInfoId);
    }
}
