package com.zzz.smo.userservice.server.converter;


import com.zzz.smo.userservice.server.entity.User;
import com.zzz.smo.userservice.server.form.UserForm;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

/**
 * @Author: Sean
 * @Date: 2019/3/14 13:48
 */
@Component
public class UserForm2User {
    public User convert(UserForm userForm){
        User user = new User();
        BeanUtils.copyProperties(userForm,user);
        return user;
    }
}
