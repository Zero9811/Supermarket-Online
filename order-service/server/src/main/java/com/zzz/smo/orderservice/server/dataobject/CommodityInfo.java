package com.zzz.smo.orderservice.server.dataobject;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: Sean
 * @Date: 2019/1/1 22:26
 */
@Data
/**
 * 商品信息表
 */
public class CommodityInfo {
    private long id;
    private String name;
    private BigDecimal price;
    private String description;
    private int type;
    private int status;
}
