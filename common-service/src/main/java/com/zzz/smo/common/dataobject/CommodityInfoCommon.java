package com.zzz.smo.common.dataobject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Sean
 * @Date: 2019/3/14 16:58
 */
@Data
public class CommodityInfoCommon {
    private long id;
    private String name;
    private BigDecimal price;
    private String description;
    private int type;
    private int status;
    //商品库存
    private int stock;
}
