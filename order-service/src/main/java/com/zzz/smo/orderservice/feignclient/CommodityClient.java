package com.zzz.smo.orderservice.feignclient;

import com.zzz.smo.orderservice.VO.ResultVO;
import com.zzz.smo.orderservice.dto.StockDTO;
import com.zzz.smo.orderservice.entity.CommodityInfo;
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
    List<CommodityInfo> findOrderList(@RequestBody List<Long> idList);

    @PostMapping("/commodity/stock/decrease")
    ResultVO reduceStock(@RequestBody List<StockDTO> stockList);
}
