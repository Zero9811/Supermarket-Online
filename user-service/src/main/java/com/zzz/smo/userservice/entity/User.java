package com.zzz.smo.userservice.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * @Author: Sean
 * @Date: 2018/12/26 15:02
 */
@Data
@Entity
public class User {
    @Id
    private String username;
    private String password;
//    private String email;
//    private String phone;
    private String nickname;
//    private int addressId;
//    private int age;
//    private String gender;
//    private double height;
//    private double weight;
}