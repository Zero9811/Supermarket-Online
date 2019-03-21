package com.zzz.smo.commodityservice.server.util;

/**
 * @Author: Sean
 * @Date: 2019/1/17 14:40
 */
public class BaseIdWorker {
    private long timestamp = System.currentTimeMillis();
    public long idWorker(){
        int temp = (int) (Math.random()*1000+2000);
        return timestamp+temp;
    }
}
