package com.boot.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.cloud.controller.LoadBalanceConsumerController
 *
 * @author lipeng
 * @date 2021/8/18 7:41 PM
 */
@RestController
public class ConsulLoadBalanceConsumerController {

    @Bean
    @LoadBalanced
    private RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/{name}")
    public String index(@PathVariable(name = "name") String name) {
        return restTemplate.getForObject("http://consul-loadbalance-provider-service/" + name, String.class);
    }
}
