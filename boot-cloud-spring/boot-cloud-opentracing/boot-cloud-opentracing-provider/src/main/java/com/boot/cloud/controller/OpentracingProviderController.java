package com.boot.cloud.controller;

import io.opentracing.util.GlobalTracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.cloud.controller.OpentracingProviderApplication
 *
 * @author lipeng
 * @date 2021/8/13 9:51 AM
 */
@RestController
public class OpentracingProviderController {

    @GetMapping("/")
    public String index() {
        return "current request trace id：" + GlobalTracer.get().activeSpan().context().toTraceId();
    }
}
