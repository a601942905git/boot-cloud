package com.boot.cloud.config;

import okhttp3.OkHttpClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.boot.cloud.config.OkHttpClientConfig
 *
 * @author lipeng
 * @date 2021/7/16 4:14 PM
 */
@Configuration
public class OkHttpClientConfig {

    @Bean
    @LoadBalanced
    public OkHttpClient.Builder okHttpClientBuilder() {
        return new OkHttpClient.Builder();
    }
}
