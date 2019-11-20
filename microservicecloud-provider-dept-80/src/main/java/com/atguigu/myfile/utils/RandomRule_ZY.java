package com.atguigu.myfile.utils;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * @author bitao
 * @desc 自定义Ribbon负载均衡器算法
 */
public class RandomRule_ZY extends AbstractLoadBalancerRule {
     //total = 0 // 当total==5以后，我们指针才能往下走，
    //total = 0 // 当前对外提供服务的服务地址，
    //total 需要重新置为零，但是已经达到过一个5次，我们的index = 1
    // 分析：我们5次，但是微服务只有8001,8002,8003 三台,OK?
    //总共被调用的次数,目前要求每台被调用5次
    private int total = 0;
    //当前提供服务的机器号
    private int currentIndex = 0;

    public Server choose(ILoadBalancer lb, Object key) {
        if(lb == null) {
            return  null;
        }
        Server server = null;
        while (server == null) {
            if(Thread.interrupted()) {
                return null;
            }
            List<Server> uplist = lb.getReachableServers();
            List<Server> allist = lb.getAllServers();
            int serverCount = allist.size();
            if(serverCount == 0) {
                return null;
            }
            //private int total = 0; //总共被调用的次数,目前要求被调用5次
            //private int currentIndex = 0; //当前提供服务的机器号
            if(total <5) {
                server = uplist.get(currentIndex);
                total++;
            }else {
                total = 0;
                currentIndex++;
                if(currentIndex >= uplist.size()) {
                    currentIndex=0;
                }
            }
            if(server == null) {
                Thread.yield();
                continue;
            }
            if(server.isAlive()) {
                return (server);
            }
            server = null;
            Thread.yield();
        }
        return server;
    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(),key);
    }
}
