package com.zzz.smo.commodityservice.server.exception;

/**
 * @Author: Sean
 * @Date: 2019/1/11 13:27
 */
public class LowStocksException extends RuntimeException {
    private int code;
    public LowStocksException(int code, String msg){
        super(msg);
        this.code = code;
    }
}
