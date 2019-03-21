package com.zzz.smo.orderservice.server.controller;

import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.orderservice.server.feignclient.UserClient;
import com.zzz.smo.userservice.client.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author: Sean
 * @Date: 2019/1/22 23:41
 */
@RestController
@Slf4j
public class MqTestController {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private UserClient userClient;
    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("/myMq")
    public void mqTest(){
        amqpTemplate.convertAndSend("myExchange","something","now is "+new Date());
    }

    //Feign回调测试
    @GetMapping("/feignTest")
    public ResultVO feignTest(String username){
        log.info("进入到feign测试");
        ResultVO resultVO = userFeignClient.findUserByUsername(username);
        log.info("调用完毕");
        return resultVO;
    }

    @GetMapping("/feignTest2")
    public ResultVO feignTest2(String username){
        ResultVO resultVO = userClient.findByName(username);
        return resultVO;
    }
}
