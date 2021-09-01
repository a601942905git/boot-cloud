package com.boot.cloud.resilience4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * PACKAGE_NAME.Resilience4JConsumerApplication
 *
 * @author lipeng
 * @date 2021/8/31 5:43 PM
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
public class Resilience4JConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4JConsumerApplication.class, args);
    }
}
