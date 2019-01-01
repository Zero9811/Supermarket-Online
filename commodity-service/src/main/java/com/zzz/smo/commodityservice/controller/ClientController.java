package com.zzz.smo.commodityservice.controller;

import com.zzz.smo.commodityservice.client.UserClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Sean
 * @Date: 2018/12/27 19:23
 */
@RestController
@Slf4j
public class ClientController {
    @Autowired
    private UserClient userClient;
    @GetMapping("/getUser")
    public String getUserMsg(){
        String response = userClient.getUser();
        log.info("response={}",response);
        return response;
    }
}
