package com.zzz.smo.commodityservice.server.controller;

import com.zzz.smo.commodityservice.server.dataobject.CommodityInfoAndStock;
import com.zzz.smo.commodityservice.server.service.StockService;
import com.zzz.smo.commodityservice.server.entity.CommodityInfo;
import com.zzz.smo.commodityservice.server.service.CommodityInfoService;
import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.common.utils.ResultVOUtil;
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
    @Autowired
    private StockService stockService;

    //查询一件商品
    @GetMapping("/{id}")
    public ResultVO getOne(@PathVariable long id){
        CommodityInfo commodityInfo = commodityInfoService.findById(id);
        ResultVO resultVO = ResultVOUtil.success(commodityInfo);
        return resultVO;
    }

    //删除一件商品，需要管理员权限
    @DeleteMapping("/{id}")
    public ResultVO deleteOne(@PathVariable long id){
        CommodityInfo commodityInfo = commodityInfoService.findById(id);
        //删除商品信息
        commodityInfoService.deleteOne(commodityInfo);
        //删除库存信息
        stockService.deleteByCommodityInfoId(commodityInfo.getId());

        return ResultVOUtil.success("删除成功");
    }

    //新增一件商品
    @PostMapping
    public ResultVO newOne(String name, BigDecimal price, String description, int type, int status){
        commodityInfoService.newCommodity(name,price,description,type,status);
        return ResultVOUtil.success(null);
    }

    /**
     * 得到某一类型的所有商品
     * @param type
     * @return
     */
    @GetMapping("/type/{type}")
    public ResultVO getByType(@PathVariable int type){
        List list = commodityInfoService.findUpSpecialTypeList(type);
        return ResultVOUtil.success(list);
    }

    //更新一件商品的信息
    @PutMapping("/commodity/status")
    public ResultVO changeStatus(long id,int status){
        commodityInfoService.changeStatus(id,status);
        return ResultVOUtil.success(null);
    }

    @PutMapping
    public ResultVO updateInfo(CommodityInfo commodityInfo){
        commodityInfoService.save(commodityInfo);
        return ResultVOUtil.success("修改商品信息成功");
    }

    @PostMapping("/orderList")
    public List<CommodityInfoAndStock> findOrderList(@RequestBody List<Long> idList){
        return commodityInfoService.findOrderList(idList);
    }
}
