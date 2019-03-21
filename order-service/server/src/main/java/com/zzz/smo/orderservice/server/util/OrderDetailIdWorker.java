package com.zzz.smo.orderservice.server.util;

/**
 * @Author: Sean
 * @Date: 2019/1/10 23:19
 */
public class OrderDetailIdWorker {
    private long timestamp = System.currentTimeMillis();

    public long idWorker(){
        return timestamp;
    }
}
