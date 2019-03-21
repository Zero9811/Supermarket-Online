package com.zzz.smo.commodityservice.server.controller;


import com.zzz.smo.commodityservice.server.entity.Stock;
import com.zzz.smo.commodityservice.server.service.StockService;
import com.zzz.smo.commodityservice.server.dataobject.StockInput;
import com.zzz.smo.commodityservice.server.dto.StockDTO;
import com.zzz.smo.commodityservice.server.exception.LowStocksException;
import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.common.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/11 9:47
 */
@RestController
@Slf4j
@RequestMapping("/stock")
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

    @PostMapping("/stock/increase")
    public ResultVO addStock(@RequestBody List<StockInput> stockInputs){
        stockService.addStock(stockInputs);
        return ResultVOUtil.success("库存增加成功");
    }

    /**
     * 按照id获取库存
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResultVO getOne(@PathVariable long id){
        Stock stock = stockService.findById(id);
        return ResultVOUtil.success(stock);
    }

    @GetMapping("/commodity/{commodityInfoId}")
    public ResultVO findByCommodity(@PathVariable long commodityInfoId){
        List<Stock> stockList = stockService.findByCommodityInfoId(commodityInfoId);
        return ResultVOUtil.success(stockList);
    }

    @DeleteMapping("/{id}")
    public ResultVO deleteOne(@PathVariable long id){
        stockService.delete(id);
        return ResultVOUtil.success("id为:"+id+" 删除成功");
    }

    @DeleteMapping("/commodity/commodityInfoId")
    public ResultVO deleteByCommodity(long commodityInfoId){
        stockService.deleteByCommodityInfoId(commodityInfoId);
        return ResultVOUtil.success("id为:"+commodityInfoId+" 删除成功");
    }
}
