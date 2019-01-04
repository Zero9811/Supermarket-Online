package com.zzz.smo.commodityservice.enums;

import lombok.Getter;

/**
 * @Author: Sean
 * @Date: 2019/1/4 17:35
 */
@Getter
public enum ResultVOCodeEnum {
    SUCCESS(0,"成功"),
    FAILER(1,"失败"),
    ;
    private int code;
    private String msg;
    ResultVOCodeEnum(int code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
