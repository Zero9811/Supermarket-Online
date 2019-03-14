package com.zzz.smo.common.dataobject;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/3/14 16:52
 */
@Data
public class UserInfoCommon {
    private String username;
    private String nickname;
    private String phone;
    private String gender;
    private int age;
    private double height;
    private double weight;
}
