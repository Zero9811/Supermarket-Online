package com.zzz.smo.orderservice.server.enums;

import lombok.Getter;

/**
 * @Author: Sean
 * @Date: 2019/1/14 12:15
 */
@Getter
public enum OrderStatusEnum {
    UNPAID(1,"未支付"),
    PAID(2,"已支付"),
    TOSENT(3,"待发货"),
    TORECEIVE(4,"待收货"),
    TOCOMMENT(5,"待评价"),
    FINISH(6,"完成"),
    CANCEL(0,"取消"),
    ;

    private int code;
    private String msg;

    OrderStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }}
