## 网关
Spring Cloud Gateway是由 Spring Boot 2.x、Spring WebFlux、Project Reactor构建而成
> Spring Cloud Gateway不能和同步模块结合使用，如spring-data、spring-security。Spring Cloud Gateway运行需要使用到Netty。

### 引入网关
```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

### 禁用网关功能
```yaml
spring:
  cloud:
    gateway:
      enabled: false
```

### 术语
- 路由：路由是网关的基本功能模块，由ID、URI、谓词集合、过滤器集合组成。当谓词结果为true，才进行路由匹配。
- 谓词：允许开发者从Http中匹配任何内容，如headers、parameters
- 过滤器：可以用来修改请求和响应内容

### 实战

#### 简单路由
请求必须是GET方式并且请求的路径必须是/feign/**，配置如下：
```yaml
spring:
  cloud:
    gateway:
      enabled: true
      routes:
        - id: boot-cloud-gateway # 路由的id
          uri: http://localhost:8093 # 路由服务uri
          predicates: # 谓词，用于过滤请求，符合条件的才进行路由
            - Method=GET # 请求方法是GET
            - Path=/feign/** # 请求的路径是/feign/**形式
```

启动步骤：
- 启动boot-cloud-eureka-server模块，也就是启动注册中心
- 启动boot-cloud-gateway-feign-app，也就是提供给网关调用的服务消费者
- 启动boot-cloud-provider，也就是启动服务提供者
- 启动boot-cloud-gateway，也就是启动网关服务

整个调用链：网关服务---> 服务消费者---> 服务提供者

查看结果：

浏览器中输入http://localhost:9071/feign/consumer/persons/，返回结果
```json
[{"id":10006,"name":"http://10.100.178.22:8083/feign/persons/测试6","age":21},{"id":10007,"name":"http://10.100.178.22:8083/feign/persons/测试7","age":22},{"id":10008,"name":"http://10.100.178.22:8083/feign/persons/测试8","age":23},{"id":10009,"name":"http://10.100.178.22:8083/feign/persons/测试9","age":24},{"id":100010,"name":"http://10.100.178.22:8083/feign/persons/测试10","age":25}]
```
可以看到网关服务已起效

### 简单过滤器
添加请求header、请求参数、响应header，配置如下：
```yaml
spring:
  cloud:
    gateway:
      enabled: true
      routes:
        - id: boot-cloud-gateway # 路由的id
          uri: http://localhost:8093 # 路由服务uri
          predicates: # 谓词，用于过滤请求，符合条件的才进行路由
            - Method=GET # 请求方法是GET
            - Path=/feign/** # 请求的路径是/feign/**形式
          filters:
            - AddRequestHeader=X-Request-Foo, Bar # 添加request header，可以在目标方法中通过request.getHeader("")打印输出查看
            - AddRequestParameter=foo, bar # 添加request parameter，可以在目标方法中通过request.getParameter("")打印输出查看
            - AddResponseHeader=X-Response-Foo, Bar # 可以在浏览器网络请求的response header中查看
```
查看结果：

浏览器中输入http://localhost:9071/feign/consumer/persons/，查看boot-cloud-gateway-feign-app服务控制台，可以看到如下打印内容
```yaml
request header X-Request-Foo value：Bar
request parameter foo value：bar
```
同时在浏览器网络请求的Response Header中可以看到新增的header
```yaml
Content-Type: application/json;charset=UTF-8
Date: Mon, 16 Sep 2019 09:07:56 GMT
transfer-encoding: chunked
X-Response-Foo: Bar
```

### Hystrix过滤器
网关启用熔断、降级、限流机制，需要添加spring-cloud-starter-netflix-hystrix依赖，配置如下：
```yaml
spring:
  cloud:
    gateway:
      enabled: true
      routes:
        - id: boot-cloud-gateway # 路由的id
          uri: http://localhost:8093 # 路由服务uri
          predicates: # 谓词，用于过滤请求，符合条件的才进行路由
            - Method=GET # 请求方法是GET
            - Path=/feign/** # 请求的路径是/feign/**形式
          filters:
            - AddRequestHeader=X-Request-Foo, Bar # 添加request header，可以在目标方法中通过request.getHeader("")打印输出查看
            - AddRequestParameter=foo, bar # 添加request parameter，可以在目标方法中通过request.getParameter("")打印输出查看
            - AddResponseHeader=X-Response-Foo, Bar # 可以在浏览器网络请求的response header中查看
            - name: Hystrix # 添加Hystrix
              args:
                name: fallbackcmd # 指定HystrixCommand commandKey
                fallbackUri: forward:/gateway/fallback1 # 指定降级的uri，可以指定系统内部的也可以指定外部系统
```
网关服务中添加降级控制器GatewayFallbackController
```java
@RestController
public class GatewayFallbackController {

    @GetMapping("/gateway/fallback1")
    public String gatewayFallback() {
        return "spring cloud gateway hystrix fallback method execute!!!";
    }
}
```

停掉boot-cloud-gateway-feign-app服务，浏览器中输入http://localhost:9071/feign/consumer/persons/，返回结果如下：
```
spring cloud gateway hystrix fallback method execute!!!
```
可以看到网关降级功能已起效

### Hystrix过滤器降级输出异常信息
修改配置
```yaml
spring:
  cloud:
    gateway:
      enabled: true
      routes:
        - id: boot-cloud-gateway # 路由的id
          uri: http://localhost:8093 # 路由服务uri
          predicates: # 谓词，用于过滤请求，符合条件的才进行路由
            - Method=GET # 请求方法是GET
            - Path=/feign/** # 请求的路径是/feign/**形式
          filters:
            - AddRequestHeader=X-Request-Foo, Bar # 添加request header，可以在目标方法中通过request.getHeader("")打印输出查看
            - AddRequestParameter=foo, bar # 添加request parameter，可以在目标方法中通过request.getParameter("")打印输出查看
            - AddResponseHeader=X-Response-Foo, Bar # 可以在浏览器网络请求的response header中查看
            - name: Hystrix # 添加Hystrix
              args:
                name: fallbackcmd # 指定HystrixCommand commandKey
                fallbackUri: forward:/gateway/fallback2 # 指定降级的uri，可以指定系统内部的也可以指定外部系统
```
控制器添加方法
```java
@RestController
public class GatewayFallbackController {

    @GetMapping("/gateway/fallback1")
    public Mono<String> fallback() {
        return Mono.just("spring cloud gateway hystrix fallback method execute!!!");
    }

    @GetMapping("/gateway/fallback2")
    public Mono<Map<String, Object>> fallback(ServerWebExchange exchange, Throwable throwable) {
        Map<String, Object> result = new HashMap<>(8);
        ServerHttpRequest request = exchange.getRequest();
        result.put("path", request.getPath().pathWithinApplication().value());
        result.put("method", request.getMethodValue());
        result.put("message", exchange.getAttribute(ServerWebExchangeUtils.HYSTRIX_EXECUTION_EXCEPTION_ATTR));
        return Mono.just(result);
    }
}
```
浏览器中输入http://localhost:9071/feign/consumer/persons/，返回结果如下：
```json
{
	"path": "/gateway/fallback2",
	"method": "GET",
	"message": {
		"cause": {
			"cause": null,
			"stackTrace": [{
				"methodName": "checkConnect",
				"fileName": "SocketChannelImpl.java",
				"lineNumber": -2,
				"className": "sun.nio.ch.SocketChannelImpl",
				"nativeMethod": true
			}, {
				"methodName": "finishConnect",
				"fileName": "SocketChannelImpl.java",
				"lineNumber": 717,
				"className": "sun.nio.ch.SocketChannelImpl",
				"nativeMethod": false
			}, {
				"methodName": "doFinishConnect",
				"fileName": "NioSocketChannel.java",
				"lineNumber": 327,
				"className": "io.netty.channel.socket.nio.NioSocketChannel",
				"nativeMethod": false
			}, {
				"methodName": "finishConnect",
				"fileName": "AbstractNioChannel.java",
				"lineNumber": 340,
				"className": "io.netty.channel.nio.AbstractNioChannel$AbstractNioUnsafe",
				"nativeMethod": false
			}, {
				"methodName": "processSelectedKey",
				"fileName": "NioEventLoop.java",
				"lineNumber": 670,
				"className": "io.netty.channel.nio.NioEventLoop",
				"nativeMethod": false
			}, {
				"methodName": "processSelectedKeysOptimized",
				"fileName": "NioEventLoop.java",
				"lineNumber": 617,
				"className": "io.netty.channel.nio.NioEventLoop",
				"nativeMethod": false
			}, {
				"methodName": "processSelectedKeys",
				"fileName": "NioEventLoop.java",
				"lineNumber": 534,
				"className": "io.netty.channel.nio.NioEventLoop",
				"nativeMethod": false
			}, {
				"methodName": "run",
				"fileName": "NioEventLoop.java",
				"lineNumber": 496,
				"className": "io.netty.channel.nio.NioEventLoop",
				"nativeMethod": false
			}, {
				"methodName": "run",
				"fileName": "SingleThreadEventExecutor.java",
				"lineNumber": 906,
				"className": "io.netty.util.concurrent.SingleThreadEventExecutor$5",
				"nativeMethod": false
			}, {
				"methodName": "run",
				"fileName": "ThreadExecutorMap.java",
				"lineNumber": 74,
				"className": "io.netty.util.internal.ThreadExecutorMap$2",
				"nativeMethod": false
			}, {
				"methodName": "run",
				"fileName": "Thread.java",
				"lineNumber": 748,
				"className": "java.lang.Thread",
				"nativeMethod": false
			}],
			"message": "Connection refused",
			"localizedMessage": "Connection refused",
			"suppressed": []
		},
		"stackTrace": [{
			"methodName": "checkConnect",
			"fileName": "SocketChannelImpl.java",
			"lineNumber": -2,
			"className": "sun.nio.ch.SocketChannelImpl",
			"nativeMethod": true
		}, {
			"methodName": "finishConnect",
			"fileName": "SocketChannelImpl.java",
			"lineNumber": 717,
			"className": "sun.nio.ch.SocketChannelImpl",
			"nativeMethod": false
		}, {
			"methodName": "doFinishConnect",
			"fileName": "NioSocketChannel.java",
			"lineNumber": 327,
			"className": "io.netty.channel.socket.nio.NioSocketChannel",
			"nativeMethod": false
		}, {
			"methodName": "finishConnect",
			"fileName": "AbstractNioChannel.java",
			"lineNumber": 340,
			"className": "io.netty.channel.nio.AbstractNioChannel$AbstractNioUnsafe",
			"nativeMethod": false
		}, {
			"methodName": "processSelectedKey",
			"fileName": "NioEventLoop.java",
			"lineNumber": 670,
			"className": "io.netty.channel.nio.NioEventLoop",
			"nativeMethod": false
		}, {
			"methodName": "processSelectedKeysOptimized",
			"fileName": "NioEventLoop.java",
			"lineNumber": 617,
			"className": "io.netty.channel.nio.NioEventLoop",
			"nativeMethod": false
		}, {
			"methodName": "processSelectedKeys",
			"fileName": "NioEventLoop.java",
			"lineNumber": 534,
			"className": "io.netty.channel.nio.NioEventLoop",
			"nativeMethod": false
		}, {
			"methodName": "run",
			"fileName": "NioEventLoop.java",
			"lineNumber": 496,
			"className": "io.netty.channel.nio.NioEventLoop",
			"nativeMethod": false
		}, {
			"methodName": "run",
			"fileName": "SingleThreadEventExecutor.java",
			"lineNumber": 906,
			"className": "io.netty.util.concurrent.SingleThreadEventExecutor$5",
			"nativeMethod": false
		}, {
			"methodName": "run",
			"fileName": "ThreadExecutorMap.java",
			"lineNumber": 74,
			"className": "io.netty.util.internal.ThreadExecutorMap$2",
			"nativeMethod": false
		}, {
			"methodName": "run",
			"fileName": "Thread.java",
			"lineNumber": 748,
			"className": "java.lang.Thread",
			"nativeMethod": false
		}],
		"message": "Connection refused: localhost/127.0.0.1:8093",
		"localizedMessage": "Connection refused: localhost/127.0.0.1:8093",
		"suppressed": []
	}
}
```
可以看到我们可以在降级方法方法中获取到异常信息，那么就可以将异常信息记录日志，方便问题排查

### 添加前缀
在请求的路径中添加前缀，比如我们的请求路径为/consumer/person，指定的前缀为/feign，那么最终的请求路径为/feign/consumer/person

修改yml配置
```yaml
spring:
  cloud:
    gateway:
      enabled: true
      routes:
        - id: boot-cloud-gateway # 路由的id
          uri: http://localhost:8093 # 路由服务uri
          predicates: # 谓词，用于过滤请求，符合条件的才进行路由
            - Method=GET # 请求方法是GET
            - Path=/feign/** # 请求的路径是/feign/**形式
          filters:
            - AddRequestHeader=X-Request-Foo, Bar # 添加request header，可以在目标方法中通过request.getHeader("")打印输出查看
            - AddRequestParameter=foo, bar # 添加request parameter，可以在目标方法中通过request.getParameter("")打印输出查看
            - AddResponseHeader=X-Response-Foo, Bar # 可以在浏览器网络请求的response header中查看
            - name: Hystrix # 添加Hystrix
              args:
                name: fallbackcmd # 指定HystrixCommand commandKey
                fallbackUri: forward:/gateway/fallback1 # 指定降级的uri，可以指定系统内部的也可以指定外部系统
            - PreserveHostHeader # 保留原主机host，而不是http设置的
        - id: boot-cloud-gateway-prefix # 路由的id
          uri: http://localhost:8093 # 路由服务uri
          predicates: # 谓词，用于过滤请求，符合条件的才进行路由
            - Method=GET # 请求方法是GET
            - Path=/consumer/** # 请求的路径是/feign/**形式
          filters:
            - PrefixPath=/feign # 添加前缀
```
在浏览器中输入http://localhost:9071/consumer/persons/，可以看到返回结果，说明前缀配置已生效

### Redis限流
添加redis依赖
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis-reactive</artifactId>
</dependency>
```
指定限流的key，实现KeyResolver接口来自定义key
```java
public class RemoteAddressKeyResolver implements KeyResolver {

    public static final String BEAN_NAME = "remoteAddressKeyResolver";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        // 基于ip进行限流
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
```

将自定的key添加到容器中
```java
@Configuration
public class RemoteAddressKeyResolverConfig {

    @Bean(name = RemoteAddressKeyResolver.BEAN_NAME)
    public RemoteAddressKeyResolver remoteAddressKeyResolver() {
        return new RemoteAddressKeyResolver();
    }
}
```
添加限流配置
```yaml
spring:
  cloud:
    gateway:
      enabled: true
      routes:
        - id: boot-cloud-gateway # 路由的id
          uri: http://localhost:8093 # 路由服务uri
          predicates: # 谓词，用于过滤请求，符合条件的才进行路由
            - Method=GET # 请求方法是GET
            - Path=/feign/** # 请求的路径是/feign/**形式
          filters:
            - AddRequestHeader=X-Request-Foo, Bar # 添加request header，可以在目标方法中通过request.getHeader("")打印输出查看
            - AddRequestParameter=foo, bar # 添加request parameter，可以在目标方法中通过request.getParameter("")打印输出查看
            - AddResponseHeader=X-Response-Foo, Bar # 可以在浏览器网络请求的response header中查看
            - name: Hystrix # 添加Hystrix
              args:
                name: fallbackcmd # 指定HystrixCommand commandKey
                fallbackUri: forward:/gateway/fallback1 # 指定降级的uri，可以指定系统内部的也可以指定外部系统。测试方法，直接停掉路由的服务
            - name: RequestRateLimiter
              args:
                redis-rate-limiter.replenishRate: 1 # 每秒产生一个令牌，也就是每秒允许一个请求通过
                redis-rate-limiter.burstCapacity: 5 # 令牌桶的容量，也就是允许通过的最大突发请求
                key-resolver: "#{@remoteAddressKeyResolver}" # 限流的key，指定bean的名称
  redis:
    host: 127.0.0.1
    port: 6379
    database: 0  
```
启动redis服务
```bash
docker run --name myredis -p 6379:6379 -d redis
```
打开浏览器，访问：http://localhost:9071/feign/consumer/persons/
![](https://wolf-heart.oss-cn-beijing.aliyuncs.com/20190827/redis-rete-limit.gif)
如上效果可以看出，如果快速点击访问，那么就会出现429错误，如果缓速点，可以正常访问，说明限流已起效