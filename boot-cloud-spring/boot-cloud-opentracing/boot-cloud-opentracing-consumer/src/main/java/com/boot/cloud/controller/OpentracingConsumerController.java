package com.boot.cloud.controller;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.cloud.controller.OpentracingConsumerController
 *
 * @author lipeng
 * @date 2021/8/13 9:51 AM
 */
@RestController
public class OpentracingConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @GetMapping("/")
    public String index() {
        Tracer tracer = GlobalTracer.get();
        Span span = tracer.activeSpan();
        span.setBaggageItem("test", "test");
        String consumerTraceId = span.context().toTraceId();
        String providerTraceId = restTemplate.getForObject("http://localhost:8081/", String.class);
        return String.format("consumer trace id：%s，provider trace id：%s", consumerTraceId, providerTraceId);
    }
}
