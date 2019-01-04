package com.zzz.smo.commodityservice.enums;

import lombok.Getter;

/**
 * 商品上下架状态
 * @Author: Sean
 * @Date: 2019/1/1 22:41
 */
@Getter
public enum CommodityStatusEnum {
    UP(0,"在架"),
    DOWN(1,"下架"),
    ;
    private int code;
    private String message;

    CommodityStatusEnum(int code,String message){
        this.code = code;
        this.message = message;
    }
}
