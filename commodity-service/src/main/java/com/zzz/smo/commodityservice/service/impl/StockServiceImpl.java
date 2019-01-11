package com.zzz.smo.commodityservice.service.impl;

import com.zzz.smo.commodityservice.VO.ResultVO;
import com.zzz.smo.commodityservice.dto.StockDTO;
import com.zzz.smo.commodityservice.entity.Stock;
import com.zzz.smo.commodityservice.exception.LowStocksException;
import com.zzz.smo.commodityservice.repository.StockRepository;
import com.zzz.smo.commodityservice.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/4 14:47
 */
@Slf4j
@Service
public class StockServiceImpl implements StockService {
    @Autowired
    private StockRepository stockRepository;

    @Override
    public List<Stock> findByCommodityInfoList(List<Long> commodityInfoList) {
        return stockRepository.findByCommodityInfoIdIn(commodityInfoList);
    }

    @Override
    public void newStock(Stock stock) {
        stockRepository.save(stock);
    }

    @Override
    @Transactional
    public ResultVO reduceStock(List<StockDTO> stockDTOList) {

        boolean flag = true;

        //查询库存
        List<Long> commodityInfoList = new ArrayList<>();
        for (StockDTO s :
                stockDTOList) {
            commodityInfoList.add(s.getCommodityInfoId());
        }
        List<Stock> stockList = stockRepository.findByCommodityInfoIdIn(commodityInfoList);

        List<StockDTO> result = new ArrayList<>();

        //判断是否满足扣库存条件
        for (StockDTO sDTO :
                stockDTOList) {
            for (Stock s:
                 stockList) {
                if(s.getQuantity()<sDTO.getNums()){
                    //库存不足
                    flag = false;
                    log.error("库存不足，商品id为 "+s.getId());
                    sDTO.setNums(s.getQuantity());
                    result.add(sDTO);
                }
            }
        }
        ResultVO resultVO = new ResultVO();
        //满足条件，开始扣库存
        if (flag){
            for (StockDTO sDTO :
                    stockDTOList) {
                for (Stock s:
                        stockList) {
                    if(s.getCommodityInfoId() == sDTO.getCommodityInfoId()){
                        s.setQuantity(s.getQuantity()-sDTO.getNums());
                    }
                }
            }
            stockRepository.saveAll(stockList);
            resultVO.setCode(0);
            resultVO.setMsg("扣库存成功");
            return resultVO;
        }
        //库存不足，返回不足的库存信息
        else {
            resultVO.setCode(1);
            resultVO.setMsg("库存不足");
            resultVO.setData(result);
            return resultVO;
        }
    }

    @Override
    public void addStock(List<StockDTO> stockDTOList) {

    }
}
