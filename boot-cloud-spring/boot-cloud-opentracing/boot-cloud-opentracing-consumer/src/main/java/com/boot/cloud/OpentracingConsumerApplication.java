package com.boot.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.cloud.OpentracingConsumerApplication
 *
 * @see org.springframework.web.client.RestTemplate 继承
 * @see org.springframework.http.client.support.InterceptingHttpAccessor InterceptingHttpAccessor持有拦截器集合
 *
 * @see io.opentracing.contrib.spring.web.starter.RestTemplateTracingAutoConfiguration
 * 注入RestTemplate，设置TracingRestTemplateInterceptor拦截器
 * 在执行http前调用TracingRestTemplateInterceptor的intercept()注入全链路信息
 *
 * @see io.opentracing.contrib.spring.web.client.TracingRestTemplateInterceptor 注入
 *
 * @see io.opentracing.contrib.web.servlet.filter.TracingFilter 提取
 * @author lipeng
 * @date 2021/8/13 9:44 AM
 */
@SpringBootApplication
public class OpentracingConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpentracingConsumerApplication.class, args);
    }
}
