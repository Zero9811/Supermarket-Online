package com.zzz.smo.orderservice.server.message;

import com.google.gson.Gson;
import com.zzz.smo.orderservice.server.dto.OrderDTO;
import com.zzz.smo.orderservice.server.entity.OrderInfo;
import com.zzz.smo.orderservice.server.service.OrderDetailService;
import com.zzz.smo.orderservice.server.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @Author: Sean
 * @Date: 2019/1/23 17:13
 */
@Component
@Slf4j
public class CreateOrderReceiver {
    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 异步生成订单
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
        value = @Queue("createOrderQueue"),
            key = "createOrder",
            exchange = @Exchange("orderExchange")
    ))
    public void createOrder(String message){
        OrderDTO orderDTO = new Gson().fromJson(message,OrderDTO.class);
        OrderInfo orderInfo = new OrderInfo();
        BeanUtils.copyProperties(orderDTO,orderInfo);
        //保存订单信息和订单详情信息
        orderDetailService.createDetails(orderDTO);
        orderInfoService.save(orderInfo);
        String redisResult = stringRedisTemplate.opsForValue().
                get(String.valueOf(orderInfo.getId()));
        //还未扣除库存
        if (StringUtils.isEmpty(redisResult)){
            stringRedisTemplate.opsForValue().
                    set(String.valueOf(orderInfo.getId()),"true");
        }
        else {
            if (redisResult.equals("true")){
                //TODO 清除Redis
            }
            else {
                //TODO 扣库存失败，需要取消订单，清除Redis
            }
        }
    }
}
