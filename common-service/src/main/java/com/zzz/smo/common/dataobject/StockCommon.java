package com.zzz.smo.common.dataobject;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/3/14 17:01
 */
@Data
public class StockCommon {
    private long stockId;
    private long commodityInfoId;
    private int quantity;
}
