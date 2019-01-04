package com.zzz.smo.commodityservice.util;

import com.zzz.smo.commodityservice.VO.ResultVO;
import com.zzz.smo.commodityservice.enums.ResultVOCodeEnum;

/**
 * @Author: Sean
 * @Date: 2019/1/4 17:30
 */
public class ResultVOUtil {
    //TODO 单例模式的改造
    public static ResultVO success(Object o){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultVOCodeEnum.SUCCESS.getCode());
        resultVO.setMsg(ResultVOCodeEnum.SUCCESS.getMsg());
        resultVO.setData(o);
        return resultVO;
    }

    public static ResultVO failer(Object o){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultVOCodeEnum.FAILER.getCode());
        resultVO.setMsg(ResultVOCodeEnum.FAILER.getMsg());
        resultVO.setData(o);
        return resultVO;
    }
}
