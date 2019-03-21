package com.zzz.smo.commodityservice.server.controller;

import com.zzz.smo.commodityservice.server.entity.ShoppingCartInfo;
import com.zzz.smo.commodityservice.server.service.ShoppingCartService;
import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.common.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/17 14:21
 */
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    /**
     * 查询一条购物车信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResultVO getOne(@PathVariable long id){
        ShoppingCartInfo cartInfo = shoppingCartService.findById(id);
        return ResultVOUtil.success(cartInfo);
    }

    @PutMapping
    public ResultVO updateInfo(ShoppingCartInfo shoppingCartInfo){
        shoppingCartService.save(shoppingCartInfo);
        return ResultVOUtil.success("购物车信息更新成功");
    }

    @PostMapping
    public ResultVO addToCart(String username, long commodityInfoId, int nums){
        ShoppingCartInfo shoppingCartInfo = new ShoppingCartInfo();
        shoppingCartInfo.setUsername(username);
        shoppingCartInfo.setCommodityInfoId(commodityInfoId);
        shoppingCartInfo.setNums(nums);
        shoppingCartService.addToCart(shoppingCartInfo);
        return ResultVOUtil.success("加入购物车成功");
    }

    @GetMapping("/user/{username}")
    public ResultVO findCart(@PathVariable String username){
        return ResultVOUtil.success(shoppingCartService.findByUsername(username));
    }

    @DeleteMapping("/{id}")
    public ResultVO deleteOne(@PathVariable long id){
        shoppingCartService.deleteOne(id);
        return ResultVOUtil.success("相关商品已移出购物车");
    }

    @DeleteMapping("/user/part")
    public ResultVO removeFromCart(List<ShoppingCartInfo> shoppingCartInfoList){
        shoppingCartService.removeFromCart(shoppingCartInfoList);
        return ResultVOUtil.success("已移除相关商品");
    }
}
