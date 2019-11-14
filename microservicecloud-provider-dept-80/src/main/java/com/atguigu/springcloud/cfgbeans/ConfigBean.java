package com.atguigu.springcloud.cfgbeans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author bitao
 */
@Configuration
public class ConfigBean {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
