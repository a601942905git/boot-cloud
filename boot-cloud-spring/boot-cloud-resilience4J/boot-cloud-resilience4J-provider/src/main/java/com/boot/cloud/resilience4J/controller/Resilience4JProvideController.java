package com.boot.cloud.resilience4J.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * com.boot.cloud.controller.Resilience4JProvideController
 *
 * @author lipeng
 * @date 2021/8/31 7:21 PM
 */
@RestController
public class Resilience4JProvideController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/test")
    public String test() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(100);
        return "resilience4J service from " + port;
    }
}
