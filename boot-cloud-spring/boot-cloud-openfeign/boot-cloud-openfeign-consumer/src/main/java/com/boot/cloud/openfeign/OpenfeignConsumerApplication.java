package com.boot.cloud.openfeign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * com.boot.cloud.openfeign.OpenfeignConsumerApplication
 *
 * 1.服务发现相关配置：
 * @see org.springframework.cloud.loadbalancer.annotation.LoadBalancerClientConfiguration
 *
 * 2.缓存相关配置
 * @see org.springframework.cloud.loadbalancer.config.LoadBalancerCacheAutoConfiguration
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
