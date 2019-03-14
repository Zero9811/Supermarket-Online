package com.zzz.smo.userservice.server.service;


import com.zzz.smo.userservice.server.entity.User;

/**
 * @Author: Sean
 * @Date: 2019/1/12 12:34
 */
public interface UserService {
    User findByUsername(String username);
    void save(User user);
    String encryPassword(String password);
}
