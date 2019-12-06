package com.atguigu.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer /*Config Server与gihub通信*/
public class MicroservicecloudConfig3344Application {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicecloudConfig3344Application.class, args);
    }

}
