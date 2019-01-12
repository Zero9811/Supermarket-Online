package com.zzz.smo.userservice.entity;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/1/12 20:55
 */
@Data
public class UserInfoOutput {
    private String nickname;
    private String gender;
    private int age;
    private double height;
    private double weight;
}
