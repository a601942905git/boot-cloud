package com.boot.cloud;

import io.jaegertracing.internal.propagation.TextMapCodec;
import io.opentracing.contrib.spring.web.client.HttpHeadersCarrier;
import io.opentracing.contrib.web.servlet.filter.HttpServletRequestExtractAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.cloud.OpentracingConsumerApplication
 *
 * 术语：
 *      1.Trace：调用链，一个调用链中可能包含多个Span
 *      2.Span：处理阶段
 *      3.SpanContext：一个Span对应一个SpanContext
 *
 * 流程：
 *
 * 客户端：
 *      1.创建Span，Span包含SpanContext
 *      2.将SpanContext中包含的信息组装成字符串 968a2dba22acc54b:caa813f90d67a45d:968a2dba22acc54b:1并放入HttpHeader中
 * 服务端：
 *      1.读取所有HttpHeader内容
 *      2.读取Header中uber-trace-id内容，并生成SpanContext
 *
 * @see org.springframework.web.client.RestTemplate 继承
 * @see org.springframework.http.client.support.InterceptingHttpAccessor InterceptingHttpAccessor持有拦截器集合
 *
 * @see io.opentracing.contrib.spring.web.starter.RestTemplateTracingAutoConfiguration
 * 注入RestTemplate，设置TracingRestTemplateInterceptor拦截器
 * 在执行http前调用TracingRestTemplateInterceptor的intercept()注入全链路信息
 *
 * @see io.opentracing.contrib.spring.web.client.TracingRestTemplateInterceptor 注入
 * @see TextMapCodec#inject(io.jaegertracing.internal.JaegerSpanContext, io.opentracing.propagation.TextMap) 注入实现，实际调用
 * @see HttpHeadersCarrier#put(java.lang.String, java.lang.String) 也就是将信息放入http header中
 *
 * @see io.opentracing.contrib.web.servlet.filter.TracingFilter 提取
 * @see HttpServletRequestExtractAdapter#servletHeadersToMultiMap(javax.servlet.http.HttpServletRequest) 读取所有header信息
 * @see TextMapCodec#extract(io.opentracing.propagation.TextMap) 读取header中uber-trace-id的值生成SpanContext
 *
 * 如果想在分布式系统之间传递自定义标识可以使用span.setBaggageItem("test", "test");，该方法设置的key和value也会被放入header中进行传递
 *
 * @author lipeng
 * @date 2021/8/13 9:44 AM
 */
@SpringBootApplication
public class OpentracingConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpentracingConsumerApplication.class, args);
    }
}
