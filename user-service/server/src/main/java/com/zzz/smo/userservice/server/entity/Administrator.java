package com.zzz.smo.userservice.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 先忽略地区店铺问题，把整个平台看做一个店铺
 * @Author: Sean
 * @Date: 2019/1/13 15:54
 */
@Data
@Entity
public class Administrator {
    @Id
    private String username;
    private String password;
    private String nickname;
    //唯一
    private String email;
    //唯一
    private String phone;
    //相关责任人的电话
    private String leaderPhone;
    //权限列表
    private String permission;
    //激活状态，未激活时，该管理员不能进行任何操作
    private int activation;
}
