package com.zzz.smo.apigateway.constant;


import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Sean
 * @Date: 2019/3/21 17:23
 */
public interface UserAuthApi {
    Map<String,String> filterMap = new HashMap(){{
        put("/myOrder/order","POST");
    }};
}
