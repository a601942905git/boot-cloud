package com.boot.cloud.hystix;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * com.boot.cloud.hystix.SpringCloudHystrixConsumerApplication
 *
 * @author lipeng
 * @dateTime 2018/11/19 上午10:48
 */
@SpringCloudApplication
@ComponentScan(basePackages = "com.boot.cloud")
public class SpringCloudHystrixConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudHystrixConsumerApplication.class, args);
    }
}
