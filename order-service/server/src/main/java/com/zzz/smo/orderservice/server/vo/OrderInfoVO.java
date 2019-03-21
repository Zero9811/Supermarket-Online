package com.zzz.smo.orderservice.server.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/6 14:02
 */
@Data
public class OrderInfoVO {
    private long id;
    private String username;
    private BigDecimal totalAmount;
    private Date createDate;
    //订单状态
    private int status;
    private List orderDetails;
}
