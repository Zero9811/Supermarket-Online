package com.zzz.smo.commodityservice.server.service.impl;

import com.zzz.smo.commodityservice.server.dataobject.CommodityInfoAndStock;
import com.zzz.smo.commodityservice.server.entity.CommodityInfo;
import com.zzz.smo.commodityservice.server.entity.Stock;
import com.zzz.smo.commodityservice.server.enums.CommodityStatusEnum;
import com.zzz.smo.commodityservice.server.service.StockService;
import com.zzz.smo.commodityservice.server.util.CommodityIdWorkerUtil;
import com.zzz.smo.commodityservice.server.repository.CommodityInfoRepository;
import com.zzz.smo.commodityservice.server.service.CommodityInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/1 22:37
 */
@Service
@Slf4j
public class CommodityInfoServiceImpl implements CommodityInfoService {
    @Autowired
    private CommodityInfoRepository commodityInfoRepository;

    @Autowired
    private StockService stockService;


    /**
     * 查找某一类别的在架商品
     * @param type
     * @return
     */
    @Override
    public List<CommodityInfo> findUpSpecialTypeList(int type) {
        return commodityInfoRepository.findByTypeAndStatus(type, CommodityStatusEnum.UP.getCode());
    }

    /**
     * 查找特定的商品
     * @param id
     * @return
     */
    @Override
    public CommodityInfo findById(long id) {
        return commodityInfoRepository.findById(id).get();
    }

    @Override
    public void newCommodity(String name, BigDecimal price, String description, int type, int status) {
        CommodityInfo commodityInfo = new CommodityInfo();



        CommodityIdWorkerUtil idWorkerUtil = new CommodityIdWorkerUtil();
        commodityInfo.setId(idWorkerUtil.idWorker(type));

        commodityInfo.setName(name);
        commodityInfo.setPrice(price);
        commodityInfo.setDescription(description);
        commodityInfo.setType(type);
        commodityInfo.setStatus(status);
        commodityInfoRepository.save(commodityInfo);
        log.info("新的商品信息已增加");
    }

    @Override
    public void changeStatus(long id, int status) {
        CommodityInfo commodityInfo = commodityInfoRepository.findById(id).get();
        commodityInfo.setStatus(status);
        commodityInfoRepository.save(commodityInfo);
        log.info("商品在架状态已改变，商品id "+commodityInfo.getId());
    }

    /**
     * 查询商品信息和库存
     * @param idList
     * @return
     */
    @Override
    public List<CommodityInfoAndStock> findOrderList(List idList) {
        List<CommodityInfo> commodityInfoList = commodityInfoRepository.findByIdIn(idList);
        List<Stock> stockList = stockService.findByCommodityInfoList(idList);
        //组合成CommodityInfoAndStock
        List<CommodityInfoAndStock> commodityInfoAndStockList = new ArrayList<>();
        for (CommodityInfo c :
                commodityInfoList) {
            for (Stock s:
                stockList) {
                if (c.getId() == s.getCommodityInfoId()){
                    CommodityInfoAndStock commodityInfoAndStock = new CommodityInfoAndStock();
                    BeanUtils.copyProperties(c,commodityInfoAndStock);
                    commodityInfoAndStock.setNums(s.getQuantity());
                    commodityInfoAndStockList.add(commodityInfoAndStock);
                }
            }
        }
        return commodityInfoAndStockList;
    }

    @Override
    public void deleteOne(CommodityInfo commodityInfo) {
        commodityInfoRepository.delete(commodityInfo);
    }

    @Override
    public void save(CommodityInfo commodityInfo) {
        commodityInfoRepository.save(commodityInfo);
    }
}
