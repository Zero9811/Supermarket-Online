package com.zzz.smo.orderservice.util;

import com.zzz.smo.orderservice.VO.ResultVO;

/**
 * @Author: Sean
 * @Date: 2019/1/9 22:50
 */
public class ResultVOUtil {
    public static ResultVO success(Object o){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("success");
        resultVO.setData(o);
        return resultVO;
    }
}
