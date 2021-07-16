package com.boot.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.square.retrofit.EnableRetrofitClients;

/**
 * com.boot.cloud.RetrofitApplication
 *
 * @author lipeng
 * @date 2021/7/16 4:13 PM
 */
@SpringBootApplication
@EnableRetrofitClients
public class RetrofitApplication {

    public static void main(String[] args) {
        SpringApplication.run(RetrofitApplication.class, args);
    }
}
