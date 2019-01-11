package com.zzz.smo.commodityservice.dto;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/1/11 9:53
 */
@Data
public class StockDTO {
    //商品id
    private long commodityInfoId;
    //商品数量
    private int nums;
}
