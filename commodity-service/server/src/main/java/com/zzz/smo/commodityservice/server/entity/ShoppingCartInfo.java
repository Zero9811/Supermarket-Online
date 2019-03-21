package com.zzz.smo.commodityservice.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Sean
 * @Date: 2019/1/17 13:14
 */
@Data
@Entity
public class ShoppingCartInfo {
    @Id
    private long id;
    private String username;
    private long commodityInfoId;
    private BigDecimal price;
    private int nums;
    private Date createDate;
}
