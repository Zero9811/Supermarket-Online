package com.zzz.smo.userservice.dataobject;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/1/12 20:55
 */
@Data
public class UserInfoOutput {
    private String username;
    private String nickname;
    private String phone;
    private String gender;
    private int age;
    private double height;
    private double weight;
}
