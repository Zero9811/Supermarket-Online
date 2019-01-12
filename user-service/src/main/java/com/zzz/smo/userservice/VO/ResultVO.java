package com.zzz.smo.userservice.VO;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/1/12 12:38
 */
@Data
public class ResultVO<T> {

    private int code;
    private String msg;
    private T data;
}
