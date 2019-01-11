package com.zzz.smo.orderservice.VO;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/1/6 14:00
 */
@Data
public class ResultVO<T> {
    private int code;
    private String msg;
    private T data;
}
