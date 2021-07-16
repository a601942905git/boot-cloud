package com.boot.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * com.boot.cloud.SpringCloudEurekaClusterServerApplication
 *
 * @author lipeng
 * @dateTime 2018/11/15 下午3:17
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringCloudEurekaClusterServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudEurekaClusterServerApplication.class, args);
    }
}
