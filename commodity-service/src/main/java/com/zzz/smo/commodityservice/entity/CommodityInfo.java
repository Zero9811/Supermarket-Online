package com.zzz.smo.commodityservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author: Sean
 * @Date: 2019/1/1 22:26
 */
@Data
@Entity
/**
 * 商品信息表
 */
public class CommodityInfo {
    @Id
    private String id;
    private String name;
    private String description;
    private int type;
    private int status;
}
