package com.zzz.smo.orderservice.server.feignclient;

import com.zzz.smo.common.VO.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Sean
 * @Date: 2019/3/16 21:39
 */
@FeignClient(name = "user-service")
public interface UserClient {

    @GetMapping("/user/{username}")
    ResultVO findByName(@PathVariable String username);
}
