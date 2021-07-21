## 1.项目相关

## 1.1 项目描述
Spring Cloud项目，用于学习Spring Cloud分布式技术

## 1.2 项目组件
- Eureka、Nacos：注册中心，用于服务的注册于发现
- Feign、Openfeign：远程调用，用于实现对服务的调用
- Ribbon、loadbalancer：客户端负载均衡，用于客户端对服务调用选择策略
- Hystrix、sentinel：熔断器，用于实现熔断、降级、限流的功能
- Gateway：网关，用于统一处理请求

## 1.3 项目版本
- Spring Boot:    2.5.2
- Spring Cloud:   2020.0.2

## 1.4 项目模块
- boot-cloud：作为父项目，用来管理依赖
  - boot-cloud-alibaba：使用alibaba组件实现
    - boot-cloud-nacos-consumer：基于nacos注册中心实现的服务消费者
    - boot-cloud-nacos-provider：基于nacos注册中心实现的服务提供者
  - boot-cloud-spring：使用springCloud组件实现
    - boot-api：服务接口
    - boot-cloud-eureka-cluster-server：eureka集群服务
    - boot-cloud-eureka-server：eureka单机服务
    - boot-cloud-provider：基于eureka实现的服务提供者
    - boot-cloud-app：基于RestTemplate实现的消费端
    - boot-cloud-feign-app：基于Feign实现的消费端
    - boot-cloud-feign-custom-app：基于Feign实现的消费端，实现对FeignClient的自定义配置，相依配置参考application.yml
    - boot-cloud-gateway：基于Spring Cloud Gateway实现的网关
    - boot-cloud-gateway-feign-app：给boot-cloud-gateway网管服务进行路由的服务消费者
    - boot-cloud-proxy：整理反向代理
- boot-cloud-http：使用http方式实现远程调用、使用反向代理简化http调用方式、仿照Mybatis结合Spring进一步简化远程过程调用

## 2. Eureka相关

### 2.1 Eureka单机与集群
- boot-cloud-eureka-server模块实现了eureka单机服务
- boot-cloud-eureka-cluster-server模块实现了eureka集群服务

### 2.2 Eureka服务配置注意点
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

## 3.服务相关
### 3.1 服务提供者单机与集群
boot-cloud-provider配置文件中基于文档块实现了多环境配置，可以单机启动，也可以集群启动

### 3.2 服务提供者集群启动方式
- IDEA配置，添加3个Spring Boot服务
```
Program arguments：--spring.profiles.active=provider-server2
```
- jar方式运行
```
java -jar xxx.jar --spring.profiles.active=provider-server1

java -jar xxx.jar --spring.profiles.active=provider-server2

java -jar xxx.jar --spring.profiles.active=provider-server3
```

## 4.alibaba相关

### 4.1 项目模块
- boot-cloud-nacos-provider：基于nacos实现的服务提供者
- boot-cloud-nacos-consumer：基于nacos实现的服务消费者

### 4.2 项目依赖
- spring-cloud-alibaba使用的版本是2.2.6.RELEASE
- spring-cloud使用的版本是2020.0.2
- 所有alibaba依赖的groupId必须是com.alibaba.cloud

### 4.3 Nacos启动注意事项
> Nacos单机启动的时候需要加上 -m standalone，否则会出现启动报错，参考地址：[follow the quick start ,there are something error #1127](https://github.com/alibaba/nacos/issues/1127)

### 4.4 负载均衡测试步骤
- 基于boot-cloud-nacos-provider模块添加3个启动项
- 分别启动3个服务提供者
- 启动boot-cloud-nacos-consumer-feign模块，访问http://localhost:9092/hello?name=nacos

### 4.5 降级测试步骤
- 基于boot-cloud-nacos-provider模块添加3个启动项
- 分别启动3个服务提供者
- 启动boot-cloud-nacos-consumer-feign-sentinel模块，访问http://localhost:9093/hello?name=nacos
- 关闭3个服务提供者
- 再次访问http://localhost:9093/hello?name=nacos

### 4.6 Sentinel面板
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

## 5.相关文章
- [带你入门RPC之反向代理](https://github.com/a601942905git/boot-cloud/tree/master/boot-cloud-proxy)
- [带你入门RPC之Http调用](https://github.com/a601942905git/boot-cloud/tree/master/boot-cloud-http-call)
- [带你入门RPC之使用反向代理简化Http调用](https://github.com/a601942905git/boot-cloud/blob/master/boot-cloud-http-call/READMD_HTTP_PROXY.md)
- [带你入门RPC值整合Spring](https://github.com/a601942905git/boot-cloud/blob/master/boot-cloud-http-call/README_SPRING.md)