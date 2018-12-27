package com.zzz.smo.userservice.controller;

import com.zzz.smo.userservice.entity.User;
import com.zzz.smo.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Sean
 * @Date: 2018/12/27 15:25
 */
@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public String getA(){
        return userRepository.findByUsername("22").toString();
    }
}
