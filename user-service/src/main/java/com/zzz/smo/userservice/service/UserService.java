package com.zzz.smo.userservice.service;

import com.zzz.smo.userservice.entity.User;

/**
 * @Author: Sean
 * @Date: 2019/1/12 12:34
 */
public interface UserService {
    User findByUsername(String username);
    void save(User user);
    String encryPassword(String password);
}
