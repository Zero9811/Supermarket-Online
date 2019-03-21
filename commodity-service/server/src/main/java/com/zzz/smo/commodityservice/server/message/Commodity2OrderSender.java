package com.zzz.smo.commodityservice.server.message;

import com.google.gson.Gson;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Sean
 * @Date: 2019/3/8 21:29
 */
@Component
public class Commodity2OrderSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void decreaseStockFeedBack(Object message){
        amqpTemplate.convertAndSend("myExchange","decreaseStockFeedback",new Gson().toJson(message));
    }
}
