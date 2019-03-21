package com.zzz.smo.orderservice.server.dataobject;


import com.zzz.smo.orderservice.server.dto.StockDTO;
import lombok.Data;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/3/8 21:44
 */
@Data
public class StockOutput {
    private long orderId;
    private List<StockDTO> stockDTOList;
}
