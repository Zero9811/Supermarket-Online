package com.zzz.smo.commodityservice.server.service.impl;

import com.zzz.smo.commodityservice.server.entity.ShoppingCartInfo;
import com.zzz.smo.commodityservice.server.repository.ShoppingCartRepository;
import com.zzz.smo.commodityservice.server.service.ShoppingCartService;
import com.zzz.smo.commodityservice.server.util.BaseIdWorker;
import com.zzz.smo.commodityservice.server.service.CommodityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Sean
 * @Date: 2019/1/17 14:09
 */
//TODO 找一种方法使购物车和商品里的价格保持一致
@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CommodityInfoService commodityInfoService;

    @Override
    public List<ShoppingCartInfo> findByUsername(String username) {
        return shoppingCartRepository.findByUsername(username);
    }

    @Override
    public void addToCart(ShoppingCartInfo shoppingCartInfo) {
        //查询是否存在
        ShoppingCartInfo cart = shoppingCartRepository.
                findByCommodityInfoId(shoppingCartInfo.getCommodityInfoId());
        if (cart != null){
            cart.setNums(shoppingCartInfo.getNums()+cart.getNums());
            shoppingCartRepository.save(cart);
        }
        else{
            shoppingCartInfo.setPrice(commodityInfoService.
                    findById(shoppingCartInfo.getCommodityInfoId()).getPrice());
            shoppingCartInfo.setId(new BaseIdWorker().idWorker());
            shoppingCartInfo.setCreateDate(new Date());
            shoppingCartRepository.save(shoppingCartInfo);
        }
    }

    @Override
    public void removeFromCart(List<ShoppingCartInfo> shoppingCartInfoList) {
        List<Long> ids = shoppingCartInfoList.stream().map(ShoppingCartInfo::getId).collect(Collectors.toList());
        shoppingCartRepository.deleteShoppingCartsByIdIn(ids);
    }

    @Override
    public void buy(List<ShoppingCartInfo> shoppingCartInfoList) {

    }

    @Override
    public ShoppingCartInfo findById(long id) {
        return shoppingCartRepository.getOne(id);
    }

    @Override
    public void save(ShoppingCartInfo shoppingCartInfo) {
        shoppingCartRepository.save(shoppingCartInfo);
    }

    @Override
    public void deleteOne(long id) {
        shoppingCartRepository.deleteById(id);
    }
}
