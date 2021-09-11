package com.boot.cloud.resilience4j.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * com.boot.cloud.resilience4j.config.Resilience4JCircuitBreakerFactoryCustomizer
 *
 * 1.Resilience4JCircuitBreakerFactory自定义配置，用于覆盖默认配置
 * @see org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JAutoConfiguration
 *
 *
 * @author lipeng
 * @date 2021/9/11 1:39 PM
 */
@Configuration
public class Resilience4JCircuitBreakerFactoryCustomizer implements Customizer<Resilience4JCircuitBreakerFactory> {

    @Override
    public void customize(Resilience4JCircuitBreakerFactory resilience4JCircuitBreakerFactory) {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .minimumNumberOfCalls(3)
                .slidingWindowSize(3)
                .failureRateThreshold(50)
                .permittedNumberOfCallsInHalfOpenState(1)
                .build();

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(10))
                .cancelRunningFuture(true)
                .build();

        resilience4JCircuitBreakerFactory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(circuitBreakerConfig)
                .timeLimiterConfig(timeLimiterConfig).build());
    }
}
