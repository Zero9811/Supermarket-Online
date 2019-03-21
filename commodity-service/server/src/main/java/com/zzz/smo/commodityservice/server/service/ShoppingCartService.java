package com.zzz.smo.commodityservice.server.service;

import com.zzz.smo.commodityservice.server.entity.ShoppingCartInfo;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/17 13:35
 */
public interface ShoppingCartService {
    List<ShoppingCartInfo> findByUsername(String username);
    void addToCart(ShoppingCartInfo shoppingCartInfo);
    void removeFromCart(List<ShoppingCartInfo> shoppingCartInfoList);
    void buy(List<ShoppingCartInfo> shoppingCartInfoList);
    ShoppingCartInfo findById(long id);
    void save(ShoppingCartInfo shoppingCartInfo);
    void deleteOne(long id);
}
