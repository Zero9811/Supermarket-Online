package com.zzz.smo.orderservice.service.impl;

import com.zzz.smo.orderservice.OrderServiceApplicationTests;
import com.zzz.smo.orderservice.entity.CommodityInfo;
import com.zzz.smo.orderservice.feignclient.CommodityClient;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author: Sean
 * @Date: 2019/1/10 20:48
 */
@Component
public class OrderInfoServiceImplTest extends OrderServiceApplicationTests {
    @Autowired
    private CommodityClient commodityClient;

    @Test
    public void createAnOrder() {

    }
    @Test
    public void getCommodityInfo(){
        List<CommodityInfo> list = commodityClient.findOrderList(Arrays.asList(Long.valueOf(11),Long.valueOf(22)));
        Assert.assertTrue(list.size()>0);
    }
}