package com.boot.cloud.resilience4j;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * com.boot.cloud.resilience4j.Resilience4JRateLimiterTest
 *
 * 1.限流配置
 *      1.1 timeoutDuration 等待时间
 *      1.2 limitRefreshPeriod 刷新时间周期
 *      1.3 limitForPeriod 允许请求数
 * 2.底层实现
 *      2.1 AtomicRateLimiter
 *      2.2 SemaphoreBasedRateLimiter
 *
 * @author lipeng
 * @date 2021/9/11 11:18 AM
 */
@Slf4j
public class Resilience4JRateLimiterTest {

    @Test
    public void test() {
        RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofMillis(1000))
                .limitForPeriod(5)
                .timeoutDuration(Duration.ofMillis(10))
                .build();

        RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(rateLimiterConfig);
        RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("hello");
        Supplier<String> supplier = RateLimiter.decorateSupplier(rateLimiter, () -> "world");
        for (int i = 0; i < 10; i++) {
            try {
                Try.ofSupplier(supplier).map(value -> "hello" + value).get();
            } catch (Exception e) {
                log.error("exception：", e);
            }
        }
    }
}
