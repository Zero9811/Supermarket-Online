package com.zzz.smo.orderservice.server.repository;


import com.zzz.smo.orderservice.server.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/6 14:07
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findByOrderInfoId(long orderInfoId);
    void deleteById(long id);
    void deleteByCommodityInfoId(long commodityInfoId);
    void deleteByOrderInfoId(long orderInfoId);
}
