package com.zzz.smo.userservice.server.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @Author: Sean
 * @Date: 2019/1/17 15:27
 */
@Data
@Entity
public class Address {
    @Id
    private long id;
    private String username;
    private String name;
    private String phone;
    private String addressContent;
    private String province;
    private String city;
    //经纬度
    private double longitude;
    private double latitude;

    public Address() {
    }

    public Address(String username, String name, String phone, String addressContent) {
        this.username = username;
        this.name = name;
        this.phone = phone;
        this.addressContent = addressContent;
    }
}
