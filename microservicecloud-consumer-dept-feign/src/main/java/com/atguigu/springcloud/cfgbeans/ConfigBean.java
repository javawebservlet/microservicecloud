package com.atguigu.springcloud.cfgbeans;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author bitao
 */
@Configuration
public class ConfigBean {

    @Bean
    @LoadBalanced//Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端 负载均衡的工具
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    /**
    RoundRobinRule(轮询算法)

    RandomRule(随机算法)

    AvailabilityFilteringRule()：会先过滤由于多次访问故障而处于断路器跳闸状态的服务，还有并发的连接数量超过阈值的服务，然后对剩余的服务列表按照轮询策略进行访问

    WeightedResponseTimeRule()：根据平均响应的时间计算所有服务的权重，响应时间越快服务权重越大被选中的概率越高，刚启动时如果统计信息不足，则使用RoundRobinRule策略，等统计信息足够会切换到WeightedResponseTimeRule

    RetryRule()：先按照RoundRobinRule的策略获取服务，如果获取失败则在制定时间内进行重试，获取可用的服务。

    BestAviableRule()：会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务

    ZoneAvoidanceRule()：默认规则，符合判断server所在区域的性能和server的可用性选择服务器
     **/

    @Bean
    public IRule rule() {
        /**
         * 随机分配
         * @return
         */
        return new RandomRule();
        //默认规则，符合判断server所在区域的性能和server的可用性选择服务器
       // return new  ZoneAvoidanceRule();
       // 会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
       // return new BestAvailableRule();
       // return new  RetryRule();
        //自定义负载均衡算法
       // return new RandomRule_ZY();
    }
}
