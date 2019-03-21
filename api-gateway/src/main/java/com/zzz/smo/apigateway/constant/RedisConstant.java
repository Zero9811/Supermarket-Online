package com.zzz.smo.apigateway.constant;

/**
 * @Author: Sean
 * @Date: 2019/1/12 22:22
 */
public interface RedisConstant {
    String USER_TOKEN_TEMPLATE = "user_token_%s";
    String ADMIN_TOKEN_TEMPLATE = "admin_token_%s";
    int EXPIRE = 7200;
}
