package com.zzz.smo.commodityservice.repository;

import com.zzz.smo.commodityservice.CommodityServiceApplicationTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: Sean
 * @Date: 2019/1/11 10:08
 */
@Component
public class StockRepositoryTest extends CommodityServiceApplicationTests {

    @Autowired
    private StockRepository stockRepository;

    @Test
    public void findStockListTest(){
        List list = stockRepository.findByCommodityInfoIdIn(Arrays.asList(Long.valueOf(2),Long.valueOf(33)));
        Assert.assertTrue(list.size()>0);
    }
}