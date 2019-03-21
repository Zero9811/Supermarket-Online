package com.zzz.smo.commodityservice.server.repository;

import com.zzz.smo.commodityservice.server.entity.CommodityInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/1 22:28
 */
public interface CommodityInfoRepository extends JpaRepository<CommodityInfo,Long> {
    /**
     * 查询在架的某一类商品的列表
     * @param type
     * @param status
     * @return
     */
    List<CommodityInfo> findByTypeAndStatus(int type,int status);
    List<CommodityInfo> findByIdIn(List list);
}
