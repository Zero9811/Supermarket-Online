package com.zzz.smo.commodityservice.controller;

import com.zzz.smo.commodityservice.VO.ResultVO;
import com.zzz.smo.commodityservice.entity.CommodityInfo;
import com.zzz.smo.commodityservice.service.CommodityInfoService;
import com.zzz.smo.commodityservice.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/4 20:37
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController {

    @Autowired
    private CommodityInfoService commodityInfoService;

    //查询一件商品
    @GetMapping("/commodity")
    public ResultVO getOne(long id){
        CommodityInfo commodityInfo = commodityInfoService.findById(id);
        ResultVO resultVO = ResultVOUtil.success(commodityInfo);
        return resultVO;
    }

    //新增一件商品
    @PostMapping("/commodity")
    public ResultVO newOne(String name, BigDecimal price, String description, int type, int status){
        commodityInfoService.newCommodity(name,price,description,type,status);
        return ResultVOUtil.success(null);
    }

    /**
     * 得到某一类型的所有商品
     * @param type
     * @return
     */
    @GetMapping("/commodities/type")
    public ResultVO getByType(int type){
        List list = commodityInfoService.findUpSpecialTypeList(type);
        return ResultVOUtil.success(list);
    }

    //更新一件商品的信息
    @PutMapping("/commodity/status")
    public ResultVO changeStatus(long id,int status){
        commodityInfoService.changeStatus(id,status);
        return ResultVOUtil.success(null);
    }

    @PostMapping("/orderList")
    public List<CommodityInfo> findOrderList(@RequestBody List<Long> idList){
        return commodityInfoService.findOrderList(idList);
    }
}
