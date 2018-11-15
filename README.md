# 项目描述
Spring Boot、Spring Cloud项目，用于学习Spring Cloud分布式技术

# 项目结构
```
|- boot-cloud
    |- boot-cloud-api               服务api层
    |- boot-cloud-app               服务消费层
        |- com
            |- boot
                |- cloud
                    |- rest         基于rest风格的远程调用
                    |- SpringCloudConsumerApplication 服务消费者启动类
                    
    |- boot-cloud-eureka-server     eureka服务
        |- com
            |- boot
                |- cloud
                    |- SpringCloudEurekaServerApplication Eureka服务启动类
                    
    |- boot-cloud-provider          服务提供层
        |- com
            |- boot
                |- cloud
                    |- rest         基于rest风格提供服务
                    |- SpringCloudProviderApplication 服务提供者启动类
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
## GET请求

### getForObject
```
restTemplate.getForObject("http://spring-cloud-provider-application/persons/", String.class)
第一个参数：远程调用的URL地址
第二个参数：远程调用返回的数据格式
第三个参数：REST风格参数值
```

### getForEntity
```
占位符使用索引方式：
ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://spring-cloud-provider-application/persons/id/{1}", String.class, id);
第一个参数：远程调用的url地址
第二个参数：返回数据类型
第三个参数：REST风格参数值

占位符使用属性名称方式：
ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://spring-cloud-provider-application/persons/name/{name}", String.class, name);

System.out.println("responseEntity.getBody()======>" + responseEntity.getBody());
System.out.println("responseEntity.getStatusCode()======>" + responseEntity.getStatusCode());
System.out.println("responseEntity.getStatusCodeValue()======>" + responseEntity.getStatusCodeValue());
System.out.println("responseEntity.getHeaders()======>" + responseEntity.getHeaders());
```

### uri方式
```
RestTemplate restTemplate = getRestTemplate();
UriComponents uriComponents = UriComponentsBuilder
        .fromUriString("http://spring-cloud-provider-application/persons/age?age={age}")
        .build()
        .expand(age)
        .encode();

URI uri = uriComponents.toUri();
ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
return responseEntity.getBody();
```

### getForObject和getForEntity的区别
如果只想获取返回结果可以使用getForObject，如果想获取额外信息可以使用getForEntity

## POST请求
```
String json = restTemplate.postForObject("http://spring-cloud-provider-application/persons/", jsonObject, String.class);

第一个参数：远程调用url地址
第二个参数：请求的参数对象
第三个参数：返回数据类型
```
## PUT请求
```
restTemplate.put("http://spring-cloud-provider-application/persons/", jsonObject);

第一个参数：远程调用url地址
第二个参数：请求的参数对象
```
## DELETE请求
```
restTemplate.delete("http://spring-cloud-provider-application/persons/{id}", id);

第一个参数：远程调用url地址
第二个参数：REST风格参数值
```
### PUT请求和DELETE请求区别
PUT和DELETE这两个请求无返回结果