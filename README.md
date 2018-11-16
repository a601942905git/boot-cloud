# 项目描述
Spring Boot、Spring Cloud项目，用于学习Spring Cloud分布式技术

# 项目结构
```
|- boot-cloud
    |- boot-cloud-api                   服务api层
    
    |- boot-cloud-app                   服务消费层
        |- com
            |- boot
                |- cloud
                    |- rest         基于rest风格的远程调用
                    |- SpringCloudConsumerApplication 服务消费者启动类
                    
    |- boot-cloud-eureka-server         eureka服务
        |- com
            |- boot
                |- cloud
                    |- SpringCloudEurekaServerApplication Eureka服务启动类
                    
    
    |- boot-cloud-eureka-cluster-server eureka集群服务
        |- com
            |- boot
                |- cloud
                    |- SpringCloudEurekaClusterServerApplication Eureka服务启动类 
                    
    |- boot-cloud-provider              服务提供层
        |- com
            |- boot
                |- cloud
                    |- rest         基于rest风格提供服务
                    |- SpringCloudProviderApplication 服务提供者启动类
```

# 关于Eureka服务说明
boot-cloud-eureka-cluster-server是eureka服务的集群，如果不想启动集群，只想
启动单个eureka服务，那么请使用boot-cloud-eureka-server模块

# 关于boot-cloud-provider
boot-cloud-provider配置文件中使用了2中配置，用于测试负载使用，如果只想启动一个，
那么可以使用默认配置，直接启动就好，端口为8080

如果想启动其中1个或者说是分别启动2个
- IDEA配置
```
启动类Active Profiles中配置为provider-server1或者provider-server2，
不配置按照默认配置启动服务

```
- jar方式运行
```
java -jar xxx.jar --spring.profiles.active=provider-server1

java -jar xxx.jar --spring.profiles.active=provider-server2
```

# Eureka服务配置注意点
示例代码配置如下
```
spring:
  application:
    name: spring-boot-cloud-eureka-server

server:
  port: 8000

eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:${server.port}/eureka # Eureka服务器端地址，如果指定端口一定要配置
    register-with-eureka: false
    fetch-registry: false
```
示例代码中指定了server.port=8000，不是默认的端口，
这个时候就必须配置eureka.client.serviceUrl.defaultZone属性，
如果不配置就会出现连接超时的错误，原因如下
```
public EurekaClientConfigBean() {
    this.serviceUrl.put("defaultZone", "http://localhost：8761/eureka/");
}
```
eureka服务默认地址为http://localhost:8761/eureka/，而我们服务的端口是8000，那么http://localhost:8761/eureka/这个
地址自然是访问不通，所以就会出现连接超时的错误。

# RestTemplate使用
[RestTemplate使用](http://note.youdao.com/noteshare?id=c2c5d2b772684d9bcc25482651b86f0b)

# Spring Cloud 注册中心高可用、服务提供者负载
[Spring Cloud 注册中心高可用、服务提供者负载](http://note.youdao.com/noteshare?id=01918897128ed49c6e4a7f4e95a5ac83)

# Spring Cloud健康监测
[Spring Cloud  Eureka客户端健康检测与常用配置](http://note.youdao.com/noteshare?id=2b677ba3e96c38f9c566b4eacc73da39)