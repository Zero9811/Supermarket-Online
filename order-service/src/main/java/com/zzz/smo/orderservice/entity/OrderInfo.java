package com.zzz.smo.orderservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: Sean
 * @Date: 2019/1/5 22:20
 */
@Data
@Entity
public class OrderInfo {
    @Id
    private long id;
    private String username;
    private BigDecimal totalAmount;
    private Date createDate;
    //订单状态
    private int status;

}
