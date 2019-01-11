package com.zzz.smo.commodityservice.repository;

import com.zzz.smo.commodityservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/3 16:20
 */
public interface StockRepository extends JpaRepository<Stock,Long> {
    Stock findByCommodityInfoIdAndWarehouseId(long commodityInfoId,long warehouseId);
    List<Stock> findByCommodityInfoIdIn(List<Long> list);
}
