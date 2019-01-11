package com.zzz.smo.commodityservice.service;

import com.zzz.smo.commodityservice.VO.ResultVO;
import com.zzz.smo.commodityservice.dto.StockDTO;
import com.zzz.smo.commodityservice.entity.Stock;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/4 14:41
 */
public interface StockService {
    List<Stock> findByCommodityInfoList(List<Long> commodityInfoList);
    void newStock(Stock stock);
    ResultVO reduceStock(List<StockDTO> stockDTOList);
    void addStock(List<StockDTO> stockDTOList);
}
