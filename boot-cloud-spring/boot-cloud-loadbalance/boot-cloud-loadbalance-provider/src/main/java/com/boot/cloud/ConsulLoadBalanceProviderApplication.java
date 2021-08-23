package com.boot.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * com.boot.cloud.LoadBalanceApplication
 *
 * @author lipeng
 * @date 2021/8/18 7:35 PM
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ConsulLoadBalanceProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsulLoadBalanceProviderApplication.class, args);
    }
}
