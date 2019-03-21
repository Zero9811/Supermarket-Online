package com.zzz.smo.commodityservice.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String CategoryName;
}
