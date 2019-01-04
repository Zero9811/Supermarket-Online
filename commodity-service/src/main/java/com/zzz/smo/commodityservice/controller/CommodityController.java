package com.zzz.smo.commodityservice.controller;

import com.zzz.smo.commodityservice.VO.ResultVO;
import com.zzz.smo.commodityservice.entity.CommodityInfo;
import com.zzz.smo.commodityservice.service.CommodityInfoService;
import com.zzz.smo.commodityservice.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/one")
    public ResultVO getOne(String id){
        CommodityInfo commodityInfo = commodityInfoService.findById(id);
        ResultVO resultVO = ResultVOUtil.success(commodityInfo);
        return resultVO;
    }

    @PostMapping("/one")
    public ResultVO newOne(String id,String name,String description,int type,int status){
        commodityInfoService.newCommodity(id,name,description,type,status);
        return ResultVOUtil.success(null);
    }

    /**
     * 得到某一类型的所有商品
     * @param type
     * @return
     */
    @GetMapping("/type")
    public ResultVO getByType(int type){
        List list = commodityInfoService.findUpSpecialTypeList(type);
        return ResultVOUtil.success(list);
    }

    @PutMapping("/status")
    public ResultVO changeStatus(String id,int status){
        commodityInfoService.changeStatus(id,status);
        return ResultVOUtil.success(null);
    }
}
