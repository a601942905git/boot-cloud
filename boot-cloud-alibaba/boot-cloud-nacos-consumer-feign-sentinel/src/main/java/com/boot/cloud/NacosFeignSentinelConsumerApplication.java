package com.boot.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * com.boot.cloud.NacosFeignConsumerApplication
 *
 * @author lipeng
 * @date 2019-08-12 10:36
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class NacosFeignSentinelConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosFeignSentinelConsumerApplication.class, args);
    }
}
