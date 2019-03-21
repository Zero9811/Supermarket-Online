package com.zzz.smo.orderservice.server.dataobject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Sean
 * @Date: 2019/1/23 16:13
 */
@Data
public class CommodityInfoAndStock {
    private long id;
    private String name;
    private BigDecimal price;
    private String description;
    private int type;
    private int status;
    //商品数量
    private int nums;
}
