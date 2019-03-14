package com.zzz.smo.userservice.form;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/3/14 13:44
 */
@Data
public class UserForm {
    private String username;
    private String phone;
    private String nickname;
    //默认收货地址
//    private long addressId;
    private int age;
    private String gender;
    private double height;
    private double weight;
}
