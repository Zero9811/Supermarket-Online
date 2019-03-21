package com.zzz.smo.commodityservice.server.repository;

import com.zzz.smo.commodityservice.server.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/3 16:20
 */
public interface StockRepository extends JpaRepository<Stock,Long> {
    Stock findByCommodityInfoIdAndWarehouseId(long commodityInfoId,long warehouseId);
    List<Stock> findByCommodityInfoIdIn(List<Long> list);
    void deleteByCommodityInfoId(long commodityInfoId);
    List<Stock> findByCommodityInfoId(long commodityInfoId);
}
