package com.zzz.smo.commodityservice.server.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Sean
 * @Date: 2018/12/27 19:18
 */
@FeignClient(name = "user-service")
public interface UserClient  {
    @GetMapping("/")
    String getUser();
}
