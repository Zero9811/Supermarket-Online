package com.zzz.smo.orderservice.server.repository;


import com.zzz.smo.orderservice.server.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/6 0:24
 */
public interface OrderInfoRepository extends JpaRepository<OrderInfo,Long> {
    List<OrderInfo> findByUsername(String username);
}
