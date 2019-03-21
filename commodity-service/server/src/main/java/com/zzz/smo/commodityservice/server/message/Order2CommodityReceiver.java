package com.zzz.smo.commodityservice.server.message;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzz.smo.commodityservice.server.dataobject.StockInput;
import com.zzz.smo.commodityservice.server.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/24 18:40
 */
@Component
@Slf4j
public class Order2CommodityReceiver {
    @Autowired
    private StockService stockService;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myExchange"),
            key = "increaseStock",
            value = @Queue("increaseStockQueue")
    ))
    public void increaseStockProcess(String message){
        //json转换
        List<StockInput> inputList = new Gson().fromJson(message,
                new TypeToken<List<StockInput>>(){}.getType());
        stockService.addStock(inputList);
    }

}
