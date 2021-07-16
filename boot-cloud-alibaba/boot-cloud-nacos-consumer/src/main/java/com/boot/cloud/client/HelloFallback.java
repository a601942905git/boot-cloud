package com.boot.cloud.client;

import org.springframework.stereotype.Component;

/**
 * com.boot.cloud.HelloFallback
 *
 * @author lipeng
 * @date 2019-08-12 11:24
 */
@Component
public class HelloFallback implements HelloClient {

    @Override
    public String hello(String name) {
        return "网络开小差，请检查网络！";
    }
}
