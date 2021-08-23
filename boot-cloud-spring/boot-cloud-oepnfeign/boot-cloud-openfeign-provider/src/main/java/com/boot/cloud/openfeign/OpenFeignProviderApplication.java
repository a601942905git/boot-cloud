package com.boot.cloud.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * com.boot.cloud.openfeign.OpenFeignProviderApplication
 *
 * @author lipeng
 * @date 2021/8/23 11:26 AM
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OpenFeignProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpenFeignProviderApplication.class, args);
    }
}
