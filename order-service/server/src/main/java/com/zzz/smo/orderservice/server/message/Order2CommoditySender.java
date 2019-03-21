package com.zzz.smo.orderservice.server.message;

import com.google.gson.Gson;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**封装消息队列操作
 * 隐藏消息队列目的地
 * @Author: Sean
 * @Date: 2019/1/24 17:49
 */
@Component
public class Order2CommoditySender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void decreaseStock(Object message){
        amqpTemplate.convertAndSend("myExchange","decreaseStock",new Gson().toJson(message));
    }

    public void increaseStock(Object message){
        amqpTemplate.convertAndSend("myExchange","increaseStock",new Gson().toJson(message));
    }
}
