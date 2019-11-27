package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entity.Dept;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.List;

/**
 * @Description: 修改microservicecloud-api工程，根据已经有的DeptClientService接口
 * @dec 新建
 *      一个实现了FallbackFactory接口的类DeptClientSerivceFallbackFactory
 * @author bitao
 * @date 2019-11-27
 */
/*@FeignClient(value = "MICROSERVICECLOUD-DEPT")*/
    /**hystrix实现服务降级*/
@FeignClient(value = "MICROSERVICECLOUD-DEPT",fallbackFactory=DeptClientServiceFallbackFactory.class)
@Cacheable
public interface DeptClientService {
    @Cacheable(value = "get")
    @RequestMapping(value = "/dept/get/{id}",method = RequestMethod.GET)
    public Dept get(@PathVariable("id") Long id);

    @RequestMapping(value = "/dept/list",method = RequestMethod.GET)
    public List<Dept> list();

    @RequestMapping(value = "/dept/add",method = RequestMethod.POST)
    public boolean add(Dept dept);
}
