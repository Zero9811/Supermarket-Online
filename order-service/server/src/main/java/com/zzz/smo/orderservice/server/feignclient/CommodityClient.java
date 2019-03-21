package com.zzz.smo.orderservice.server.feignclient;


import com.zzz.smo.common.VO.ResultVO;
import com.zzz.smo.orderservice.server.dataobject.CommodityInfoAndStock;
import com.zzz.smo.orderservice.server.dto.StockDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author: Sean
 * @Date: 2019/1/10 13:47
 */
@FeignClient(name = "commodity-service")
public interface CommodityClient {

    @PostMapping("/commodity/orderList")
    List<CommodityInfoAndStock> findOrderList(@RequestBody List<Long> idList);

    @PostMapping("/commodity/stock/decrease")
    ResultVO reduceStock(@RequestBody List<StockDTO> stockList);

    @PostMapping("/commodity/stock/increase")
    ResultVO addStock(@RequestBody List<StockDTO> stockInputs);
}
