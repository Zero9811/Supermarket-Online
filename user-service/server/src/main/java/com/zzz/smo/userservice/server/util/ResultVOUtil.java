package com.zzz.smo.userservice.server.util;


import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.common.enums.ResultVOCodeEnum;

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

    public static ResultVO fail(Object o){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultVOCodeEnum.FAIL.getCode());
        resultVO.setMsg(ResultVOCodeEnum.FAIL.getMsg());
        resultVO.setData(o);
        return resultVO;
    }
}
