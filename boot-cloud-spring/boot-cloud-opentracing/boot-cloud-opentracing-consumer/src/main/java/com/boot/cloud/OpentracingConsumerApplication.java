package com.boot.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.cloud.OpentracingConsumerApplication
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
