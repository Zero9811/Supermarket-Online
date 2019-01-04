package com.zzz.smo.commodityservice.VO;

import lombok.Data;

/**
 * http请求返回的最外层对象
 * @Author: Sean
 * @Date: 2019/1/1 23:01
 */
@Data
public class ResultVO<T> {
    //错误码
    private int code;
    //提示信息
    private String msg;
    //具体数据
    private T data;
}
