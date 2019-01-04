package com.zzz.smo.commodityservice.service;

import com.zzz.smo.commodityservice.entity.CommodityInfo;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/1 22:33
 */
public interface CommodityInfoService {
    /**
     * 查询在架的某一类商品的列表
     * @param type
     * @return
     */
    List<CommodityInfo> findUpSpecialTypeList(int type);
    CommodityInfo findById(String id);
    void newCommodity(String id,String name,String description,int type,int status);
    void changeStatus(String id,int status);
}
