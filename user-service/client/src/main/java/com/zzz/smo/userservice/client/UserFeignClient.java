package com.zzz.smo.userservice.client;

import com.zzz.smo.common.VO.ResultVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author: Sean
 * @Date: 2019/3/14 16:50
 */
@FeignClient(name = "user-service",fallback = UserFeignClient.UserFeignClientFallback.class)
public interface UserFeignClient {
    @GetMapping("/address/user")
    ResultVO findAddressByUsername(String username);

    @GetMapping("/address/{id}")
    ResultVO findAddressById(@PathVariable long id);

    @GetMapping("/user/{username}")
    ResultVO findUserByUsername(@PathVariable String username);

    @Component
    class UserFeignClientFallback implements UserFeignClient{
        @Override
        public ResultVO findAddressByUsername(String username){
            return null;
        }

        @Override
        public ResultVO findAddressById(long id){
            return null;
        }

        @Override
        public ResultVO findUserByUsername(String username){
            return null;
        }
    }
}
