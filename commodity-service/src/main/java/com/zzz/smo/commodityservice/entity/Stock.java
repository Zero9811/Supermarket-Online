package com.zzz.smo.commodityservice.entity;

/**
 * @Author: Sean
 * @Date: 2019/1/3 16:04
 */

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 商品库存表
 */
@Data
@Entity
public class Stock {
    @Id
    private int id;
    private String commodityId;
    private int quantity;
    private int warehouseId;
}
