package com.atguigu.springcloud;

import com.atguigu.myfile.MyFileIRules;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name="MICROSERVICECLOUD-DEPT",configuration=MyFileIRules.class)
public class MicroservicecloudProviderDept80Application {

    public static void main(String[] args) {
        SpringApplication.run(MicroservicecloudProviderDept80Application.class, args);
    }

}
