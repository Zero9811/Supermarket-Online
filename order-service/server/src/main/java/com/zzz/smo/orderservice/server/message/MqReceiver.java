package com.zzz.smo.orderservice.server.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 接收mq消息
 * @Author: Sean
 * @Date: 2019/1/22 23:38
 */
@Component
@Slf4j
public class MqReceiver {

    /**
     * 只能监听已存在的队列，不能自己新建队列
     * @param message
     */
//    @RabbitListener(queues ="myQueue1")
//    public void process(String message){
//        log.info("myQueue: {}",message);
//    }

    /**
     * 自动创建队列并监听
     * @param message
     */
//    @RabbitListener(queuesToDeclare = @Queue("myQueue"))
//    public void process1(String message){
//        log.info("myQueue: {}",message);
//    }

    /**
     * 自动创建exchange和队列绑定
     * @param message
     */
//    @RabbitListener(bindings = @QueueBinding(
//            value = @Queue("myQueue"),
//            key = "something",
//            exchange = @Exchange("myExchange")
//    ))
//    public void process(Object message){
//        log.info("myQueue: {}",message);
//    }
}
