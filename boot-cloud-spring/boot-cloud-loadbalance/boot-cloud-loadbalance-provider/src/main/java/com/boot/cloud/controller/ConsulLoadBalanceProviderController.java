package com.boot.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.cloud.controller.LoadBalanceController
 *
 * @author lipeng
 * @date 2021/8/18 7:36 PM
 */
@RestController
public class ConsulLoadBalanceProviderController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/index/{name}")
    public String index(@PathVariable(name = "name") String name) {
        return "hello " + name + " from " + port;
    }
}
