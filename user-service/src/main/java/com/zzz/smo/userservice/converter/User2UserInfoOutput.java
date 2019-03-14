package com.zzz.smo.userservice.converter;

import com.zzz.smo.userservice.dataobject.UserInfoOutput;
import com.zzz.smo.userservice.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: Sean
 * @Date: 2019/3/14 11:31
 */
@Component
public class User2UserInfoOutput {
    public UserInfoOutput convert(User user){
        UserInfoOutput userInfoOutput = new UserInfoOutput();
        BeanUtils.copyProperties(user,userInfoOutput);
        return userInfoOutput;
    }
}
