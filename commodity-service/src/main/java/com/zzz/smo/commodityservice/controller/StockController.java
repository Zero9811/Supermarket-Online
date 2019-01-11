package com.zzz.smo.commodityservice.controller;

import com.zzz.smo.commodityservice.VO.ResultVO;
import com.zzz.smo.commodityservice.dto.StockDTO;
import com.zzz.smo.commodityservice.exception.LowStocksException;
import com.zzz.smo.commodityservice.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/11 9:47
 */
@RestController
@Slf4j
@RequestMapping("/commodity")
public class StockController {

    @Autowired
    private StockService stockService;

    /**
     * 供订单服务调用,有待改进，当库存不足时的处理方式
     * @param stockDTOList
     * @return
     */
    @PostMapping("/stock/decrease")
    public ResultVO reduceStock(@RequestBody List<StockDTO> stockDTOList){
        ResultVO resultVO = stockService.reduceStock(stockDTOList);
        if (resultVO.getCode() == 1)
            throw new LowStocksException(1,"库存不足");
        return resultVO;
    }

}
