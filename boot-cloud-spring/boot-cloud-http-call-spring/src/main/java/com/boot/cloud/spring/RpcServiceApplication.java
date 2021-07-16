package com.boot.cloud.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.cloud.spring.RpcServiceApplication
 *
 * @author lipeng
 * @date 2020/4/4 10:45 AM
 */
@SpringBootApplication
@EnableRpcService(basePackages = "com.boot.cloud.spring.third")
public class RpcServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RpcServiceApplication.class);
    }
}
