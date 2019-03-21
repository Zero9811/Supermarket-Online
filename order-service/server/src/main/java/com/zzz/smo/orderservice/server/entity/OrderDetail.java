package com.zzz.smo.orderservice.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Sean
 * @Date: 2019/1/6 13:49
 */
@Data
@Entity
public class OrderDetail {
    @Id
    private long id;
    private long orderInfoId;
    private long commodityInfoId;
    //件数
    private int nums;
    private BigDecimal price;
    private Date createDate;
}
