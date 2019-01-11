package com.zzz.smo.commodityservice.service.impl;

import com.zzz.smo.commodityservice.entity.CommodityInfo;
import com.zzz.smo.commodityservice.enums.CommodityStatusEnum;
import com.zzz.smo.commodityservice.repository.CommodityInfoRepository;
import com.zzz.smo.commodityservice.service.CommodityInfoService;
import com.zzz.smo.commodityservice.util.CommodityIdWorkerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    @Override
    public List<CommodityInfo> findOrderList(List idList) {
        return commodityInfoRepository.findByIdIn(idList);
    }
}
