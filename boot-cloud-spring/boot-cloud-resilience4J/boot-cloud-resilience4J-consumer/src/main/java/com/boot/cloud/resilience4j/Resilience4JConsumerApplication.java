package com.boot.cloud.resilience4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * PACKAGE_NAME.Resilience4JConsumerApplication
 *
 * @see io.github.resilience4j.circuitbreaker.autoconfigure.CircuitBreakerProperties 熔断器配置、实例声明
 * @see org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JAutoConfiguration 熔断器自动配置
 * @see io.github.resilience4j.circuitbreaker.autoconfigure.AbstractCircuitBreakerConfigurationOnMissingBean 熔断器注册表配置
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
