package com.zzz.smo.commodityservice.server.util;


/**
 * @Author: Sean
 * @Date: 2019/1/5 21:01
 */
public class CommodityIdWorkerUtil {
    private long timestamp = System.currentTimeMillis();

    public long idWorker(int type){
        return timestamp + type;
    }
}
