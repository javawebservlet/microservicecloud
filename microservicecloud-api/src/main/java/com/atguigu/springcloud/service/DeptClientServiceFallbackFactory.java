package com.atguigu.springcloud.service;

import com.atguigu.springcloud.entity.Dept;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @desc 实现回调方法，服务降级
 * @author bitao
 */
@Component /**不要忘记添加，不要忘记添加*/
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {

    private static final Logger logger = LoggerFactory.getLogger(DeptClientServiceFallbackFactory.class);

    @Override
    public DeptClientService create(Throwable cause) {

        return new DeptClientService() {
            @Override
            public Dept get(Long id) {
                return new Dept().setDeptno(id).setDname("该ID,"+id+",没有没有对应的信息,Consumer客户端提供的降级信息,此刻服务Provider已经关闭")
                                  .setDbSource("no this database in MySQL");
            }

            @Override
            public List<Dept> list() {
                return null;
            }

            @Override
            public boolean add(Dept dept) {
                return false;
            }
        };
    }
}
