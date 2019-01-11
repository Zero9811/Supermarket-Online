package com.zzz.smo.orderservice.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zzz.smo.orderservice.dto.OrderDTO;
import com.zzz.smo.orderservice.entity.OrderDetail;
import com.zzz.smo.orderservice.exception.OrderException;
import com.zzz.smo.orderservice.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/9 22:18
 */
@Slf4j
public class OrderForm2OrderDTOConverter {
    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setName(orderForm.getName());
        orderDTO.setPhone(orderForm.getPhone());
        orderDTO.setAddress(orderForm.getAddress());
        orderDTO.setUsername(orderForm.getUsername());

        List<OrderDetail> detailList;
        Gson gson = new Gson();
        try {
            //这一句需要多看看
           detailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【json转换】错误，string={}",orderForm.getItems());
            throw new OrderException(1,"json参数转换出错");
        }
        orderDTO.setDetails(detailList);

        return orderDTO;
    }
}
