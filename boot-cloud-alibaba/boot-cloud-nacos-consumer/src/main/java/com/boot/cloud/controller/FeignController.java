package com.boot.cloud.controller;

import com.boot.cloud.client.HelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.cloud.controller.FeignController
 *
 * 1.Feign负载自动配置类：
 * @see org.springframework.cloud.openfeign.loadbalancer.FeignLoadBalancerAutoConfiguration
 *
 * 2.负载客户端自动配置类：
 * @see org.springframework.cloud.loadbalancer.config.BlockingLoadBalancerClientAutoConfiguration
 *
 * 3.执行远程调用：
 * @see org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient
 *
 * @author lipeng
 * @date 2021/7/16 5:56 PM
 */
@RestController
@RequestMapping("/feign")
public class FeignController {

    @Autowired
    private HelloClient helloClient;

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return helloClient.hello(name);
    }
}
