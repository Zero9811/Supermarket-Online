package com.zzz.smo.orderservice.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: Sean
 * @Date: 2019/1/8 21:53
 */
@Data
public class OrderForm {

    private String username;
    @NotEmpty(message = "收货人必填")
    private String name;
    @NotEmpty(message = "手机号必填")
    private String phone;
    @NotEmpty(message = "收货地址必填")
    private String address;
    @NotEmpty(message = "购买内容不能为空")
    private String items;
}
