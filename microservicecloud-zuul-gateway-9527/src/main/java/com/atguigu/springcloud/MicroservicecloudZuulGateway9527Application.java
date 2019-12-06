package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy //路由器
public class MicroservicecloudZuulGateway9527Application {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicecloudZuulGateway9527Application.class, args);
    }

}
