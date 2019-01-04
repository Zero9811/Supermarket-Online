package com.zzz.smo.commodityservice.service;

import com.zzz.smo.commodityservice.entity.Stock;

/**
 * @Author: Sean
 * @Date: 2019/1/4 14:41
 */
public interface StockService {
    int reduceStock(int quantity,Stock stock);
    int addStock(int quantity,Stock stock);
    Stock findSuitableStock(int quantity,String commodityId);
    Stock findOne(String commodityId,int warehouseId);
    void newStock(int quantity,String commodityId,int warehouseId);
}
