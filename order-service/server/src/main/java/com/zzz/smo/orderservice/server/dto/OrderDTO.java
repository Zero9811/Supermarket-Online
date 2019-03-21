package com.zzz.smo.orderservice.server.dto;


import com.zzz.smo.orderservice.server.entity.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/9 15:17
 */
@Data
public class OrderDTO {
    private String username;
    private String name;
    private String phone;
    private String address;
    private long id;
    private BigDecimal totalAmount;
    private Date createDate;
    //订单状态
    private int status;
    private List<OrderDetail> details;
}
