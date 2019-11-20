package com.atguigu.myfile;

import com.atguigu.myfile.utils.RandomRule_ZY;
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyFileIRules {

    @Bean
    public IRule iRule(){
        return new RandomRule_ZY();
    }
}
