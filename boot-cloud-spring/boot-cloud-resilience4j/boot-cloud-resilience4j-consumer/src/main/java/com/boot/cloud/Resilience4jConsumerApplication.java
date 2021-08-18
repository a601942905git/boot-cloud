package com.boot.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * com.boot.cloud.Resilience4jConsumerApplication
 *
 * 1.官方文档：https://resilience4j.readme.io/docs/getting-started-3
 *
 * @author lipeng
 * @date 2021/8/17 6:04 PM
 */
@SpringBootApplication
public class Resilience4jConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(Resilience4jConsumerApplication.class, args);
    }
}
