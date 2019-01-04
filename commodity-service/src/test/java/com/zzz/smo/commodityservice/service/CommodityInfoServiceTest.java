package com.zzz.smo.commodityservice.service;

import com.zzz.smo.commodityservice.CommodityServiceApplicationTests;
import com.zzz.smo.commodityservice.repository.StockRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/1 22:49
 */
@Component
public class CommodityInfoServiceTest extends CommodityServiceApplicationTests {

    @Autowired
    private CommodityInfoService commodityInfoService;
    @Test
    public void findUpSpecialTypeListTest(){
        List list = commodityInfoService.findUpSpecialTypeList(1);
        Assert.assertTrue(list.size()>0);
    }

    @Autowired
    private StockRepository stockRepository;

}
