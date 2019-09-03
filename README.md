## 项目描述
Spring Boot、Spring Cloud项目，用于学习Spring Cloud分布式技术

## 项目使用版本
```
Spring Boot:    2.1.6.RELEASE
Spring Cloud:   Greenwich.SR2
```

## 一、基于Netflix项目模块说明
- boot-cloud：作为父项目，用来管理依赖
- boot-cloud-app：基于RestTemplate实现的消费端
- boot-cloud-eureka-cluster-server：实现eureka集群服务
- boot-cloud-eureka-server：实现eureka单机服务
- boot-cloud-feign-app：基于Feign实现的消费端
- boot-cloud-app：基于Feign自定义配置实现的消费端
- boot-cloud-provider：基于eureka实现的提供者

### 关于Eureka服务说明
boot-cloud-eureka-cluster-server是eureka服务的集群，如果不想启动集群，只想
启动单个eureka服务，那么请使用boot-cloud-eureka-server模块

### 关于boot-cloud-provider
boot-cloud-provider配置文件中基于文档块实现了多环境配置，可以将项目打好包。
分别激活profiles: provider-server1、  profiles: provider-server2、  profiles: provider-server3
就可以部署3个服务提供者，用于测试负载效果


如果想启动其中1个或者说是分别启动2个
- IDEA配置
```
启动类Active Profiles中配置为provider-server1或者provider-server2或者provider-server3，
不配置按照默认配置启动服务

```
- jar方式运行
```
java -jar xxx.jar --spring.profiles.active=provider-server1

java -jar xxx.jar --spring.profiles.active=provider-server2

java -jar xxx.jar --spring.profiles.active=provider-server3
```

### Eureka服务配置注意点
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

## 二、基于Alibaba项目模块说明
- boot-cloud-nacos-provider：基于nacos实现的服务提供者
- boot-cloud-nacos-consumer-rest：基于rest和ncos实现的消费端
- boot-cloud-nacos-consumer-feign：基于feign和ncos实现的消费端
- boot-cloud-nacos-consumer-feign-sentinel：基于feign、sentinel和ncos实现的消费端
- boot-cloud-nacos-gateway：通过网关访问服务

### 依赖说明
- spring-cloud-alibaba使用的版本是2.1.0.RELEASE
- spring-cloud使用的版本是Greenwich.SR2
- 所有alibaba依赖的groupId必须是com.alibaba.cloud

### Nacos启动注意事项
> Nacos单机启动的时候需要加上 -m standalone，否则会出现启动报错，参考地址：[follow the quick start ,there are something error #1127](https://github.com/alibaba/nacos/issues/1127)

### 负载均衡测试步骤
- 基于boot-cloud-nacos-provider模块添加3个启动项
- 分别启动3个服务提供者
- 启动boot-cloud-nacos-consumer-feign模块，访问http://localhost:9092/hello?name=nacos

### 降级测试步骤
- 基于boot-cloud-nacos-provider模块添加3个启动项
- 分别启动3个服务提供者
- 启动boot-cloud-nacos-consumer-feign-sentinel模块，访问http://localhost:9093/hello?name=nacos
- 关闭3个服务提供者
- 再次访问http://localhost:9093/hello?name=nacos

### Sentinel面板
- 下载sentinel-dashboard，[sentinel-dashboard-1.6.3.jar](https://github.com/alibaba/Sentinel/releases/download/1.6.3/sentinel-dashboard-1.6.3.jar)
- 启动sentinel-dashboard
```shell
java -Dserver.port=8077 -Dcsp.sentinel.dashboard.server=localhost:8077 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.6.3.jar
```
- 项目配置与sentinel-dashboard交互
```yaml
spring:
  cloud:
    sentinel:
      transport:
        port: 8719
        dashboard: 127.0.0.1:8077
```
- 启动boot-cloud-nacos-provider服务提供者
- 启动boot-cloud-nacos-consumer-feign-sentinel服务消费者
- 访问http://localhost:9093/hello?name=nacos
- 观察sentinel-dashboard

## 示例
- [RestTemplate使用](http://note.youdao.com/noteshare?id=c2c5d2b772684d9bcc25482651b86f0b)
- [Spring Cloud 注册中心高可用、服务提供者负载](http://note.youdao.com/noteshare?id=01918897128ed49c6e4a7f4e95a5ac83)
- [Spring Cloud  Eureka客户端健康检测与常用配置](http://note.youdao.com/noteshare?id=2b677ba3e96c38f9c566b4eacc73da39)
- [Spring Cloud Hystrix简单应用](http://note.youdao.com/noteshare?id=d4285c8eb8cd52e5b83db79118c9e819)