package com.zzz.smo.common.dataobject;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/3/14 16:54
 */
@Data
public class AddressInfoCommon {
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
}
