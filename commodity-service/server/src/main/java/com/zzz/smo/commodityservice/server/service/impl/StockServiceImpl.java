package com.zzz.smo.commodityservice.server.service.impl;

import com.zzz.smo.commodityservice.server.entity.Stock;
import com.zzz.smo.commodityservice.server.dataobject.StockInput;
import com.zzz.smo.commodityservice.server.dto.StockDTO;
import com.zzz.smo.commodityservice.server.repository.StockRepository;
import com.zzz.smo.commodityservice.server.service.StockService;
import com.zzz.smo.commodityservice.server.util.BaseIdWorker;
import com.zzz.smo.common.VO.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        stock.setId(new BaseIdWorker().idWorker());
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
    public void addStock(List<StockInput> stockInputs) {
        //获取id
        List<Long> idList = stockInputs.stream().
                map(StockInput :: getCommodityInfoId).collect(Collectors.toList());
        //查询列表
        List<Stock> stockList = stockRepository.findByCommodityInfoIdIn(idList);
        //修改库存
        for (Stock stock :
                stockList) {
            for (StockInput stockInput :
                    stockInputs) {
                if (stock.getCommodityInfoId() == stockInput.getCommodityInfoId())
                    stock.setQuantity(stock.getQuantity()+stockInput.getNums());
            }
        }
        //保存到数据库
        stockRepository.saveAll(stockList);
    }

    @Override
    public void deleteByCommodityInfoId(long commodityInfoId) {
        stockRepository.deleteByCommodityInfoId(commodityInfoId);
    }

    @Override
    public Stock findById(long id) {
        return stockRepository.getOne(id);
    }

    @Override
    public List<Stock> findByCommodityInfoId(long commodityInfoId) {
        return stockRepository.findByCommodityInfoId(commodityInfoId);
    }

    @Override
    public void delete(long id) {
        stockRepository.deleteById(id);
    }
}
