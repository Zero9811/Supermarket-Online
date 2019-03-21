package com.zzz.smo.orderservice.server.util;

/**
 * @Author: Sean
 * @Date: 2019/1/9 23:00
 */
public class OrderInfoIdWorker {
    private long timestamp = System.currentTimeMillis();

    public  long idWorker(){
        return timestamp;
    }
}
