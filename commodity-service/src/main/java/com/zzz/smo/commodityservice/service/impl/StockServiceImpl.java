package com.zzz.smo.commodityservice.service.impl;

import com.zzz.smo.commodityservice.entity.Stock;
import com.zzz.smo.commodityservice.repository.StockRepository;
import com.zzz.smo.commodityservice.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    /**
     * 扣库存方法，传入要扣的数量，商品id和仓库id
     * 返回仓库id
     * @param quantity
     * @return
     */
    @Override
    public int reduceStock(int quantity, Stock stock) {
        stock.setQuantity(stock.getQuantity()-quantity);
        stockRepository.save(stock);
        log.info("库存减少 "+stock.getQuantity());
        return stock.getWarehouseId();
    }

    /**
     * 增加库存，传入要增加的数量，商品id和仓库id
     * @param quantity
     * @param stock
     * @return
     */
    @Override
    public int addStock(int quantity, Stock stock) {
        stock.setQuantity(stock.getQuantity()+quantity);
        stockRepository.save(stock);
        log.info("库存增加 "+stock.getQuantity());
        return stock.getQuantity();
    }

    /**
     * 待优化
     * 查找符合扣库存要求的一组库存数据
     * @param quantity
     * @param commodityId
     * @return
     */
    @Override
    public Stock findSuitableStock(int quantity, String commodityId) {
        List<Stock> list = stockRepository.findByCommodityId(commodityId);
        for (Stock temp :
                list) {
            if (temp.getQuantity() > quantity) {
                log.info("已找到合适的库存信息",temp);
                return temp;
            }
        }
        log.info("未找到合适的库存");
        return null;
    }

    /**
     * 查找特定的一个库存记录
     * @param commodityId
     * @param warehouseId
     * @return
     */
    @Override
    public Stock findOne(String commodityId, int warehouseId) {

        return stockRepository.findByCommodityIdAndWarehouseId(commodityId,warehouseId);
    }

    @Override
    public void newStock(int quantity, String commodityId, int warehouseId) {
        Stock stock = new Stock();
        stock.setQuantity(quantity);
        stock.setCommodityId(commodityId);
        stock.setWarehouseId(warehouseId);
        stockRepository.save(stock);
        log.info("新增一条库存记录");
    }
}
