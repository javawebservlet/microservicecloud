package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entity.Dept;
import com.atguigu.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author bitao
 */
@RestController
public class DeptController_Consumer {
    /**
     * 定义访问者地址
     */
    //private static final String REST_URL_PREFIX="http://localhost:8001";
    /**
     * Application和客户端的控制层地址是一样的 private static final String REST_URL_PREFIX="http://MICROSERVICECLOUD-DEPT";
     */
   // private static final String REST_URL_PREFIX="http://MICROSERVICECLOUD-DEPT";
    /**
    *
   使用restTemplate访问restful接口非常的简单粗暴无脑。
            (url,requestMap,ResponseBean.class)这三个参数分别代表
    REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。
    */
//    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private DeptClientService service;

    @RequestMapping(value = "/consumer/dept/add")
    public Boolean add(Dept dept) {
        return this.service.add(dept);
    }
    @RequestMapping(value = "/consumer/dept/get/{id}")
    public Dept get(@PathVariable("id") Long id){

        return this.service.get(id);
    }
    @RequestMapping(value = "/consumer/dept/list")
    public List<Dept> list() {
        return this.service.list();
    }

//    /**
//     * 测试 @EnableDiscoveryClient,消费端可以调用服务发现
//     * @return
//     */
//    @RequestMapping("/consumer/dept/discovery")
//    public Object discovery(){
//        return restTemplate.getForObject(REST_URL_PREFIX+"/dept/discovery",Object.class);
//    }
}
