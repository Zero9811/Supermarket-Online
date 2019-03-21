package com.zzz.smo.orderservice.server.dto;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/1/11 12:05
 */
@Data
public class StockDTO {
    private long commodityInfoId;
    private int nums;

    public StockDTO(){

    }

    public StockDTO(long commodityInfoId,int nums){
        this.commodityInfoId = commodityInfoId;
        this.nums = nums;
    }
}
