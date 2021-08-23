package com.boot.cloud.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * com.boot.cloud.openfeign.OpenfeignConsumerApplication
 *
 * @author lipeng
 * @date 2021/8/23 11:30 AM
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class OpenfeignConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenfeignConsumerApplication.class, args);
    }
}
