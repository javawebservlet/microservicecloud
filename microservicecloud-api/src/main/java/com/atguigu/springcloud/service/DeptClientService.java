package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entity.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;

/**
 * @Description: 修改microservicecloud-api工程，根据已经有的DeptClientService接口
 * @author bitao
 */
@FeignClient(value = "MICROSERVICECLOUD-DEPT")
public interface DeptClientService {
    @RequestMapping(value = "/dept/get/{id}",method = RequestMethod.GET)
    public Dept get(@PathVariable("id") Long id);

    @RequestMapping(value = "/dept/list",method = RequestMethod.GET)
    public List<Dept> list();

    @RequestMapping(value = "/dept/add",method = RequestMethod.POST)
    public boolean add(Dept dept);
}
