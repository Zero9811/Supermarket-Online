package com.zzz.smo.commodityservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author: Sean
 * @Date: 2019/1/1 20:48
 */
@Data
@Entity
/**
 * 商品类目表
 */
public class Category {
    @Id
    private int id;
    private String CategoryName;
}
