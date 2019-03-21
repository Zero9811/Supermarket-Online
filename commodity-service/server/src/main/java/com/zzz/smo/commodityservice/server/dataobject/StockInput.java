package com.zzz.smo.commodityservice.server.dataobject;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/1/15 20:51
 */
@Data
public class StockInput {
    //商品id
    private long commodityInfoId;
    //商品数量
    private int nums;
}
