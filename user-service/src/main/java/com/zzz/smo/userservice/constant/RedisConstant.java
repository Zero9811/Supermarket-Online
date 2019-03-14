package com.zzz.smo.userservice.constant;

/**
 * @Author: Sean
 * @Date: 2019/1/12 22:22
 */
public interface RedisConstant {
    String TOKEN_TEMPLATE = "user_token_%s";
    String ADMIN_TOKEN = "admin_token_%s";
    String SMS_TOKEN = "sms_token_%s";
    int EXPIRE = 7200;

}
