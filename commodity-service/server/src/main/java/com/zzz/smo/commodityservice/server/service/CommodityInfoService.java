package com.zzz.smo.commodityservice.server.service;

import com.zzz.smo.commodityservice.server.dataobject.CommodityInfoAndStock;
import com.zzz.smo.commodityservice.server.entity.CommodityInfo;

import java.math.BigDecimal;
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
    CommodityInfo findById(long id);
    void newCommodity(String name, BigDecimal price, String description, int type, int status);
    void changeStatus(long id,int status);
    List<CommodityInfoAndStock> findOrderList(List idList);
    void deleteOne(CommodityInfo commodityInfo);
    void save(CommodityInfo commodityInfo);
}
