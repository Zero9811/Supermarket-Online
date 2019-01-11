package com.zzz.smo.commodityservice.util;


/**
 * @Author: Sean
 * @Date: 2019/1/5 21:01
 */
public class CommodityIdWorkerUtil {
    private static long timestamp = System.currentTimeMillis();

    public static long idWorker(int type){
        return timestamp + type;
    }
}
