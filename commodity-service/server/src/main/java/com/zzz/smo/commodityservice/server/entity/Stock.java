package com.zzz.smo.commodityservice.server.entity;

/**
 * @Author: Sean
 * @Date: 2019/1/3 16:04
 */

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 商品库存表
 * 目前只有一个仓库，id与commodityId相同
 */
@Data
@Entity
public class Stock {
    @Id
    private long id;
    private long commodityInfoId;
    private int quantity;
    private long warehouseId;
}
