package com.zzz.smo.commodityservice.server.service;

import com.zzz.smo.commodityservice.server.entity.Stock;
import com.zzz.smo.commodityservice.server.dataobject.StockInput;
import com.zzz.smo.commodityservice.server.dto.StockDTO;
import com.zzz.smo.common.VO.ResultVO;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/4 14:41
 */
public interface StockService {
    List<Stock> findByCommodityInfoList(List<Long> commodityInfoList);
    void newStock(Stock stock);
    ResultVO reduceStock(List<StockDTO> stockDTOList);
    void addStock(List<StockInput> stockInputs);
    void deleteByCommodityInfoId(long commodityInfoId);
    Stock findById(long id);
    List<Stock> findByCommodityInfoId(long commodityInfoId);
    void delete(long id);
}
