# microservicecloud
**微服务父子架构**
microservicecloud-provider-dept-8001:微服务提供者
microservicecloud-provider-dept-80:微服务消费者
RestTemplate提供了多种便捷访问远程Http服务的方法，
是一种简单便捷的访问restful服务模板类，是Spring提供的用于访问Rest服务的客户端模板工具集
官方地址
https://docs.spring.io/spring-framework/docs/4.3.7.RELEASE/javadoc-api/org/springframework/web/client/RestTempla
使用
使用restTemplate访问restful接口非常的简单粗暴无脑。
(url,requestMap,ResponseBean.class)这三个参数分别代表
REST请求地址、请求参数、HTTP响应转换被转换成的对象类型。

**Eureka是什么**
Eureka是Netflix的一个子模块，也是核心模块之一。Eureka是一个基于REST的服务，用于定位服务，以实现云端中间层服务发现和故障
转移。服务注册与发现对于微服务架构来说是非常重要的，有了服务发现与注册。只需要使用服务的标识符，就可以访问到服务。
而不需要修改服务调用的配置文件了。功能类似于dubbo的注册中心。比如Zookeeper.
Eureka采用了C-S的设计架构。Eureka Server作为服务注册功能的服务器，它是服务注册中心。

Eureka包含两个组件：Eureka Server和Eureka Client
Eureka Server提供服务注册服务
各个节点启动后，会在EurekaServer中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观的看到

EurekaClient是一个java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的，使用轮询(round-robin)负载算法
的负载均衡器。在应启动后，将会向Eureka Server发送心跳(默认周期为30秒)。如果Eureka Server在多个心跳周期内没有接收到
某个节点的心跳，EurekaServer将会从服务注册表表中把这个服务节点移除(默认90秒)

**eureka服务端配置：**
server:
  port: 7001

eureka:
  instance:
    hostname: localhost #eureka服务端的实例名称
  client:
    register-with-eureka: false #false表示不向注册中心注册自己。
    fetch-registry: false #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址（单机）。
**eureka自我保护机制：**
（1）一句话：某时刻某一个微服务不可用了，eureka不会立刻清理，依旧会对该服务的信息进行保存。
（2）默认情况下，如果eurekaServer在一定时间内没有接收到某个微服务实例的心跳，EurekaServer将会注销该实例(默认90秒)。
但是当网络分区故障发生时，微服务与EurekaServer之间无法正常通信，以上行为可能变得非常危险了-----因为微服务本身其实是健康的，此时本不应该注销这个微服务。Eureka通过“自我保护模式”来解决这个问题----当EurekaServer节点在短时间内丢失过多客户端时(可能发生了网络分区故障)，那么这个节点就会进入自我保护模式。一旦进入该模式，EurekaServer就会保护服务注册表中的信息，不再删除服务注册表中的数据（也就是不会注销任何微服务）。当网络故障恢复后，该Eureka Server节点会自动退出自我保护模式。
（3）在自我保护模式中，Eureka Server会保护服务注册表中的信息，不再注销任何服务实例。当它收到的心跳数重新恢复到阈值以上时，该Eureka Server节点就会自动退出自我保护模式。它的设计哲学就是宁可保留错误的服务注册信息，在不盲目注销任何可能健康的服务实例。一句话讲解：好死不如赖活着
**配置eureka集群相关配置**
1.在C:\Windows\System32\drivers\etc路径下修改hosts文件
127.0.0.1 eureka7001.com
127.0.0.1 eureka7002.com
127.0.0.1 eureka7003.com
127.0.0.1 myzuul.com
127.0.0.1 config-3344.com
127.0.0.1 client-config.com
**eureka7001配置**
server: 
  port: 7001
 
eureka: 
  instance:
    #hostname: localhost 单机
    hostname: eureka7001.com #eureka服务端的实例名称 hostname: eureka7001.com：集群
  client: 
    register-with-eureka: false     #false表示不向注册中心注册自己。
    fetch-registry: false     #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url: 
       #单机defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/       #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址（单机）。
      defaultZone: http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
**eureka7002配置**
server: 
  port: 7002
 
eureka: 
  instance:
    #hostname: localhost 单机
    hostname: eureka7002.com #eureka服务端的实例名称 hostname: eureka7001.com：集群
  client: 
    register-with-eureka: false     #false表示不向注册中心注册自己。
    fetch-registry: false     #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url: 
       #单机defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/       #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址（单机）。
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7003.com:7003/eureka/

**eureka7003配置**

server: 
  port: 7003
 
eureka: 
  instance:
    #hostname: localhost 单机
    hostname: eureka7003.com #eureka服务端的实例名称 hostname: eureka7001.com：集群
  client: 
    register-with-eureka: false     #false表示不向注册中心注册自己。
    fetch-registry: false     #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    service-url: 
       #单机defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/       #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址（单机）。
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/
**dept-8001配置**
server:
  port: 8001
mybatis:
  config-location: classpath:mybatis/mybatis.cfg.xml        # mybatis配置文件所在路径
  type-aliases-package: com.atguigu.springcloud.entity      # 所有Entity别名类所在包
  mapper-locations:
    - classpath:mybatis/mapper/**/*.xml                      # mapper映射文件
spring:
  application:
    name: microservicecloud-dept                            # 服务名称
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource            # 当前数据源操作类型
    driver-class-name: com.mysql.cj.jdbc.Driver              # mysql驱动包
    url: jdbc:mysql://localhost:3306/cloudDB01?serverTimezone=UTC             # 数据库名称
    username: root
    password: 123456
    dbcp2:
      min-idle: 5                                           # 数据库连接池的最小维持连接数
      initial-size: 5                                       # 初始化连接数
      max-total: 5                                          # 最大连接数
      max-wait-millis: 200                                  # 等待连接获取的最大超时时间
eureka:
  client: #客户端注册进eureka服务列表内
    service-url:
      #defaultZone: http://localhost:7001/eureka
       defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
  instance:
    instance-id: microservicecloud-dept8001 #Status 主机名称：服务名称修改
    prefer-ip-address: true #访问信息有IP信息提示，访问路径可以显示IP地址
#actuator与注册微服务信息完善
info:
  app.name: atguigu-microservicecloud
  company.name: www.atguigu.com
  build.artifactId: $project.artifactId$
  build.version: $project.version$
  
  microservicecloud-eureka-7002
  microservicecloud-eureka-7003
  **断路器**
  Hystrix特性
  **1.断路器机制**
  
  断路器很好理解, 当Hystrix Command请求后端服务失败数量超过一定比例(默认50%), 
  断路器会切换到开路状态(Open). 这时所有请求会直接失败而不会发送到后端服务. 
  断路器保持在开路状态一段时间后(默认5秒), 自动切换到半开路状态(HALF-OPEN).
   这时会判断下一次请求的返回情况, 如果请求成功, 断路器切回闭路状态(CLOSED), 
   否则重新切换到开路状态(OPEN). Hystrix的断路器就像我们家庭电路中的保险丝, 
   一旦后端服务不可用, 断路器会直接切断请求链, 避免发送大量无效请求影响系统吞吐量, 
   并且断路器有自我检测并恢复的能力.
  
  **`2.Fallback`**
  
  Fallback相当于是降级操作. 对于查询操作, 我们可以实现一个fallback方法, 当请求后端服务出现异常的时候, 
  可以使用fallback方法返回的值. fallback方法的返回值一般是设置的默认值或者来自缓存.
  
  **3.资源隔离**
  
  在Hystrix中, 主要通过线程池来实现资源隔离. 通常在使用的时候我们会根据调用的远程服务划分出多个线程池. 
  例如调用产品服务的Command放入A线程池, 调用账户服务的Command放入B线程池. 这样做的主要优点是运行环境被隔离开了.
   这样就算调用服务的代码存在bug或者由于其他原因导致自己所在线程池被耗尽时, 不会对系统的其他服务造成影响. 
   但是带来的代价就是维护多个线程池会对系统带来额外的性能开销. 如果是对性能有严格要求而且确信自己调用服务的客户端代码不会出问题的话, 
   可以使用Hystrix的信号模式(Semaphores)来隔离资源.
   
**服务降级**
所谓降级，一般是从整体负载考虑。就是当某个服务器熔断之后，服务器将不再被调用。
此时客户端可以自己准备一个本地的fallback回调，返回一个缺省值。
这样做，虽然服务水平下降，但好歹可用，比直接挂掉要强。
**服务监控HystrixDashboard**
除了隔离依赖服务的调用以外，Hystrix还是提供了准实时的调用监控(Hystrix Dashboard),Hystrix会持续地记录所有通过Hystrix发起的请求的执行信息，
并以统计报表和图形的形式展示给用户，包括每秒执行多少请求多少成功，多少失败等。Netflix通过hystrix-metrics-event-stream项目实现了对以上指标的监控。
SpringCloud也提供了Hystrix Dashboard的整合，对监控内容转化成可视化界面。
**自测：http://localhost:9001/hystrix**
 **服务端和客户端启动后的访问地址： http://localhost:8001/hystrix.stream**

**ZUUL路由网关访问映射规则**
概述：
Zuul包含了对请求的路由和过滤两个最主要的功能：
其中路由功能负责将外部请求转发到具体的微服务实例上，是实现外部访问统一入口的基础而过滤功能则负责请求的处理
过程进行干预，是实现请求校验、服务聚合等功能的基础.Zuul和Eureka进行整合，将Zuul自身注册为Eureka服务治理下的应用同时从Eureka中获得其他微服务的消息，也即以后的访问微服务都是通过Zuul跳转后获得。
注意：Zuul服务最终还是会注册进Eureka

提供 = 代理 + 路由 + 过滤三大功能
**路由基础浏览地址：http://myzuul.com:9527/microservicecloud-dept/dept/get/2**

**zuul:
  prefix: /atguigu #设置统一公共前缀
  ignored-services: microservicecloud-dept #忽略掉真实微服务地址(单个) 
  #ignored-services: "*" #如果有多个要忽略掉真实微服务地址就用这个(多个)
  routes:
    mydept.serviceId: microservicecloud-dept #真实地址(域名映射)
    mydept.path: /mydept/** #真实地址映射**
    
    http://myzuul.com:9527/atguigu/mydept/dept/get/2 加前缀后的地址
    
 
 **SpringCloud_Config概述：
 一、就目前而言，对于微服务业界并没有一个统一的、标准的定义(While there is no precise definition of this architectural style)
 二、但通常而言，微服务架构是一种架构模式或者说是一种架构风格，它提倡将单一应用程序划分成一组小的服务**
 **SpingCloud Config分为服务端和客户端**