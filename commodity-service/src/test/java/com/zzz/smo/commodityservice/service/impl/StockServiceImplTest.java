package com.zzz.smo.commodityservice.service.impl;

import com.zzz.smo.commodityservice.CommodityServiceApplicationTests;
import com.zzz.smo.commodityservice.entity.Stock;
import com.zzz.smo.commodityservice.repository.StockRepository;
import com.zzz.smo.commodityservice.service.StockService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.junit.Assert.*;

/**
 * @Author: Sean
 * @Date: 2019/1/4 15:09
 */
@Component
public class StockServiceImplTest extends CommodityServiceApplicationTests {
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private StockService stockService;

    @Test
    public void reduceStock() {
        int quantity = 2;
        String com = "33";
        int ware = 2;
        int result = stockService.reduceStock(2,stockService.findSuitableStock(quantity,com));
    }

    @Test
    public void addStock() {
        int quantity = 2;
        String com = "33";
        int ware = 2;
        int result = stockService.addStock(quantity,stockService.findOne(com,ware));
    }

    @Test
    public void findSuitableStock() {
        int quantity = 2;
        String com = "33";
        Stock stock = stockService.findSuitableStock(quantity,com);
        System.out.println(stock.toString());
    }
}