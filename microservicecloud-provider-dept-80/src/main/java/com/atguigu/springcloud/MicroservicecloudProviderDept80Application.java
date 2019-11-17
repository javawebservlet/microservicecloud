package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class MicroservicecloudProviderDept80Application {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicecloudProviderDept80Application.class, args);
    }

}
