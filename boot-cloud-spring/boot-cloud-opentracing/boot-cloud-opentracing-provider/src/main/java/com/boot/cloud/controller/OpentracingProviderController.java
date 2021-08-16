package com.boot.cloud.controller;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.cloud.controller.OpentracingProviderApplication
 *
 * @author lipeng
 * @date 2021/8/13 9:51 AM
 */
@RestController
@Slf4j
public class OpentracingProviderController {

    @GetMapping("/")
    public String index() {
        Tracer tracer = GlobalTracer.get();
        Span span = tracer.activeSpan();
        log.info("test：{}", span.getBaggageItem("test"));
        return "current request trace id：" + span.context().toTraceId();
    }
}
