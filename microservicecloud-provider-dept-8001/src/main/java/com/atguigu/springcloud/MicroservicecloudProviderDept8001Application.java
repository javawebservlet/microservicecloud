package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //自动注册
@EnableDiscoveryClient //服务发现
public class MicroservicecloudProviderDept8001Application {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicecloudProviderDept8001Application.class, args);
    }

}
