package com.zzz.smo.commodityservice.server.message;

import com.google.gson.Gson;
import com.zzz.smo.commodityservice.server.dataobject.DecreaseStockResult;
import com.zzz.smo.commodityservice.server.dataobject.StockOutput;
import com.zzz.smo.commodityservice.server.dto.StockDTO;
import com.zzz.smo.commodityservice.server.service.StockService;
import com.zzz.smo.common.VO.ResultVO;
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
 * @Date: 2019/1/23 0:32
 */
@Component
@Slf4j
public class DecreaseStockReceiver {
    @Autowired
    private StockService stockService;
    @Autowired
    private Commodity2OrderSender commodity2OrderSender;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("decreaseStockQueue"),
            key = "decreaseStock",
            exchange = @Exchange("myExchange")
    ))
    public void process(String message){
        log.info("decreaseStockQueue: {}",message);
        //错误尝试
        //List<StockInput> inputList = new Gson().fromJson((JsonElement) message,new TypeToken<List<StockInput>>(){}.getType());
        //log.info("size : ",inputList.size());

//        JsonObject jsonObject = (JsonObject) message;
//        JsonArray jsonArray = jsonObject.getAsJsonArray("Body");
//        List<StockInput> inputList = new Gson().fromJson(jsonArray,new TypeToken<List<StockInput>>(){}.getType());
//        log.info("size : ",inputList.size());
//        StockDTO stockDTO = new Gson().fromJson(message,StockDTO.class);
//        log.info("得到的为 {}",stockDTO);
        //暂时不用
        //List<StockDTO> inputList = new Gson().fromJson(message,new TypeToken<List<StockDTO>>(){}.getType());
        StockOutput stockOutput = new Gson().fromJson(message,StockOutput.class);
        List<StockDTO> inputList = stockOutput.getStockDTOList();
        log.info("list 长度为 {}",inputList.size());

        ResultVO resultVO = stockService.reduceStock(inputList);
        DecreaseStockResult decreaseStockResult = new DecreaseStockResult();
        //扣库存成功
        if (resultVO.getCode()==0){

            decreaseStockResult.setOrderId(stockOutput.getOrderId());
            decreaseStockResult.setDecreaseResult(true);
            commodity2OrderSender.decreaseStockFeedBack(decreaseStockResult);
        }
        //扣除失败
        else{
            decreaseStockResult.setOrderId(stockOutput.getOrderId());
            decreaseStockResult.setDecreaseResult(false);
            commodity2OrderSender.decreaseStockFeedBack(decreaseStockResult);
        }
    }
}
