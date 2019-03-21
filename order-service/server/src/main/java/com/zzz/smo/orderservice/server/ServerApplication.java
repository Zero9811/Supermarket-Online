package com.zzz.smo.orderservice.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

//@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients(basePackages ={"com.zzz.smo.orderservice.server.feignclient",
                                    "com.zzz.smo.userservice.client"} )
//@EnableCircuitBreaker
@SpringCloudApplication
@EnableHystrixDashboard
@Slf4j
@EnableSwagger2
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

}
