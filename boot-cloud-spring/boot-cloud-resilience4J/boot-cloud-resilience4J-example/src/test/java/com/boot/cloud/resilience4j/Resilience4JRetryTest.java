package com.boot.cloud.resilience4j;

import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;

/**
 * com.boot.cloud.resilience4j.Resilience4JRetryTest
 *
 * 1.重试配置
 *      1.1 maxAttempts 最大重试次数
 *      1.2 waitDuration 重试时间间隔
 *
 * @author lipeng
 * @date 2021/9/11 11:51 AM
 */
@Slf4j
public class Resilience4JRetryTest {

    @Test
    public void test() {
        RetryConfig retryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(1))
                .build();

        RetryRegistry retryRegistry = RetryRegistry.of(retryConfig);
        Retry retry = retryRegistry.retry("hello");
        retry.executeSupplier(() -> {
            log.info("execute exception task......");
            throw new NullPointerException();
        });
    }
}
