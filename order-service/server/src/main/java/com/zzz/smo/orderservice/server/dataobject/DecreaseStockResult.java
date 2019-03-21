package com.zzz.smo.orderservice.server.dataobject;

import lombok.Data;

/**
 * @Author: Sean
 * @Date: 2019/3/8 21:54
 */
@Data
public class DecreaseStockResult {
    private long orderId;
    private boolean decreaseResult;
    public boolean getDecreaseResult(){
        return decreaseResult;
    }
}
