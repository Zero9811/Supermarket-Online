package com.zzz.smo.userservice.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @Author: Sean
 * @Date: 2019/1/13 20:39
 */
@Getter
public enum PermissionEnum {
    BASE("0","基础权限"),
    COMM("1","和用户交流"),
    ORDER("2","操作订单"),
    STOCK("3","操作库存"),
    COMMODITY("4","操作商品信息"),
    USER("5","操作用户信息"),
    ADMIN("6","操作管理员信息"),
    ALL("7","所有的权限"),
    ;
    private String code;
    private String msg;

    PermissionEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }}
