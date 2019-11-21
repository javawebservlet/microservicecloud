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
  
