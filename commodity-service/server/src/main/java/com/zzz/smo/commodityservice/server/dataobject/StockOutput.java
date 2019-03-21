package com.zzz.smo.commodityservice.server.dataobject;

import com.zzz.smo.commodityservice.server.dto.StockDTO;
import lombok.Data;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/3/8 21:47
 */
@Data
public class StockOutput {
    private long orderId;
    private List<StockDTO> stockDTOList;
}
