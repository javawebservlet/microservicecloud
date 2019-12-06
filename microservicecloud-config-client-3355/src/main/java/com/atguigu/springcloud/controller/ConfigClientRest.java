package com.atguigu.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author bitao
 * @date 2019-12-05
 */
@RestController
public class ConfigClientRest {
    @Value("spring.application.name")
    private String applicationName;

    @Value("eureka.client.service-url.defaultZone")
    private String eurekaServers;

    @Value("server.port")
    private String port;

    @RequestMapping("/config")
    public String getConfig(){
        String str = "applicatonName:"+applicationName+"\t eurekaServers:"+eurekaServers+"\t port"+port;
        System.out.println("****************** str:"+str);
        return "applicatonName:"+applicationName+"\t eurekaServers:"+eurekaServers+"\t port:"+port;
    }
}
