package com.zzz.smo.userservice.service.impl;

import com.zzz.smo.userservice.entity.User;
import com.zzz.smo.userservice.repository.UserRepository;
import com.zzz.smo.userservice.service.UserService;
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
}
