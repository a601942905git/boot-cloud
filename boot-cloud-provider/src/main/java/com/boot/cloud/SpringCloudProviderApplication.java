package com.boot.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * com.boot.cloud.SpringCloudApplication
 *
 * @author lipeng
 * @dateTime 2018/11/12 下午5:01
 */
@SpringBootApplication
@EnableEurekaClient
public class SpringCloudProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudProviderApplication.class, args);
    }
}
