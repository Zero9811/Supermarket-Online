package com.zzz.smo.commodityservice.server.repository;

import com.zzz.smo.commodityservice.server.entity.ShoppingCartInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/17 13:29
 */
public interface ShoppingCartRepository extends JpaRepository<ShoppingCartInfo,Long> {
    List<ShoppingCartInfo> findByUsername(String username);
    void deleteShoppingCartsByIdIn(List<Long> ids);
    ShoppingCartInfo findByCommodityInfoId(long commodityInfoId);
}
