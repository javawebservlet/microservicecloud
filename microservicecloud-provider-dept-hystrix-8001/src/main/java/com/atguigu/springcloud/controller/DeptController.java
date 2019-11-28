package com.atguigu.springcloud.controller;

import com.atguigu.springcloud.entity.Dept;
import com.atguigu.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @desc hystrix：服务熔断机制
 * @author bitao
 * @date 2019-11-27
 */
@RestController
public class DeptController {
	@Autowired
	private DeptService service=null;

	@RequestMapping(value = "/dept/get/{id}",method = RequestMethod.GET)
	/**
	 * 一旦调用服务方达失败并抛出了错误信息后，会自动调用@HystrixCommand标注好的fallbackMethod调用类中的指定方法
	 */
	@HystrixCommand(fallbackMethod = "processHystrix_Get")
	public Dept get(@PathVariable("id") Long id) {
		Dept dept = this.service.get(id);
		if(dept == null) {
			throw  new RuntimeException("没有查询到该ID："+id+"对应的信息");
		}
		return dept;
	}

	public Dept processHystrix_Get(@PathVariable("id") Long id){

		return  new Dept().setDeptno(id).setDname("该ID:"+id+"没有对应的信息,null ~~~~@HystrixCommand")
				          .setDbSource("no this dataSource in Mysql！");
	}
	/*@RequestMapping(value = "/dept/list",method = RequestMethod.GET)
	@HystrixCommand(fallbackMethod = "processHystrixList_Get")
	public List<Dept> list() {
		List<Dept> list = this.service.list();
		if(list.isEmpty()) {
			throw new RuntimeException("没有查询到相关信息!");
		}
		return list;
	}
	public List<Dept> processHystrixList_Get(){

		return new ArrayList<Dept>();
	}*/
}
