package com.zzz.smo.userservice.server.service.impl;

import com.zzz.smo.userservice.server.entity.User;
import com.zzz.smo.userservice.server.repository.UserRepository;
import com.zzz.smo.userservice.server.service.UserService;
import com.zzz.smo.userservice.server.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Sean
 * @Date: 2019/1/12 12:36
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public String encryPassword(String password) {
        return HashUtil.encryPassword(password);
    }
}
