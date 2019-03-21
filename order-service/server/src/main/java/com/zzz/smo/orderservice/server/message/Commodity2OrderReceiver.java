package com.zzz.smo.orderservice.server.message;

import com.google.gson.Gson;
import com.zzz.smo.orderservice.server.dataobject.DecreaseStockResult;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author: Sean
 * @Date: 2019/3/8 21:18
 */
@Component
public class Commodity2OrderReceiver {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("decreaseStockFeedbackQueue"),
            key = "decreaseStockFeedback",
            exchange = @Exchange("myExchange")
    ))
    public void createOrderFeedBack(String message){
        DecreaseStockResult decreaseStockResult = new Gson().
                fromJson(message,DecreaseStockResult.class);
        //对比Redis的值
        String redisResult = stringRedisTemplate.opsForValue().
                get(String.valueOf(decreaseStockResult.getOrderId()));
        //每个操作都要清除Redis
        if(!StringUtils.isEmpty(redisResult)) {
            if (redisResult.equals("false") && !decreaseStockResult.getDecreaseResult()) {
                //TODO 扣库存和创建订单都失败,清除Redis
            } else if (redisResult.equals("false") && decreaseStockResult.getDecreaseResult()) {
                //TODO 创建订单失败，扣库存成功，需要把库存再加回去,清除Redis
            } else if (redisResult.equals("true") && !decreaseStockResult.getDecreaseResult()) {
                //TODO 创建订单成功，扣库存失败，需要把订单再删除,清除Redis
            }
            //TODO 清除Redis
        }
        //Redis为空，还未创建订单
        else{
            if (decreaseStockResult.getDecreaseResult())
            stringRedisTemplate.opsForValue().
                    set(String.valueOf(decreaseStockResult.getOrderId()),"true");
            else
                stringRedisTemplate.opsForValue().
                        set(String.valueOf(decreaseStockResult.getOrderId()),"false");
        }
    }

}
