package com.boot.cloud.resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.CheckedFunction0;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * com.boot.cloud.resilience4j.ResilienceCircuitBreakerTest
 *
 * 1.熔断器状态：CLOSED、OPEN、HALF_OPEN
 *      1.1 初始状态为CLOSED，当失败率超过阀值，变更为OPEN
 *      1.2 等待一段时间后，由OPEN变更为HALF_OPEN
 *      1.3 HALF_OPEN状态下，当失败率超过阀值，变更为OPEN
 *      1.4 HALF_OPEN状态下，当失败率低于阀值，变更为CLOSED
 *
 *  2.滑动窗口模式：
 *      2.1 基于计数，聚合最后N次调用结果
 *      2.2 基于时间，聚合最近N秒调用结果
 *
 *  3.核心配置：
 *      3.1 failureRateThreshold：失败率阀值，默认值50
 *      3.2 slowCallRateThreshold：慢调用阀值，默认值100
 *      3.3 slowCallDurationThreshold：慢调用持续时间阀值，默认值60000ms
 *      3.4 permittedNumberOfCallsInHalfOpenState：熔断器半开状态下允许通过的请求数，默认值10
 *      3.5 maxWaitDurationInHalfOpenState：熔断器在切换到打开状态之前可以保持在半打开状态的最长时间，默认值0表示在半开状态无限等待，直到所有允许请求完成
 *      3.6 slidingWindowType：滑动窗口类型，默认值COUNT_BASED
 *      3.7 slidingWindowSize：滑动窗口大小，默认值100
 *      3.8 minimumNumberOfCalls：计算失败率最小请求数，未达到最小请求数，不会计算失败率，默认值100
 *      3.9 waitDurationInOpenState：熔断器OPEN至HALF_OPEN等待时间，默认值60000ms
 *      3.10 automaticTransitionFromOpenToHalfOpenEnabled：默认值false
 *      3.11 recordExceptions：记录失败的异常，默认值empty
 *      3.12 ignoreExceptions：忽略异常，默认值empty
 *      3.13 recordFailurePredicate：默认值true，所有异常记录为失败
 *      3.14 ignoreException：默认值false，没有异常被忽略
 *
 *
 * @see io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry 存放熔断器配置集合
 * @see io.github.resilience4j.core.registry.InMemoryRegistryStore 存放熔断器集合
 *
 * @author lipeng
 * @date 2021/9/1 11:02 AM
 */
public class ResilienceCircuitBreakerTest {

    /**
     * 流程：
     *      1.创建熔断器配置CircuitBreakerConfig
     *      2.创建熔断器注册表CircuitBreakerRegistry
     *      3.创建熔断器CircuitBreaker
     *      4.创建任务
     *      5.使用Try执行
     */
    @Test
    public void test() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .minimumNumberOfCalls(3)
                .build();

        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("hello");
        CheckedFunction0<String> decoratedSupplier = CircuitBreaker.decorateCheckedSupplier(circuitBreaker,
                () -> "This can be any method which returns: 'Hello");

        Try<String> result = Try.of(decoratedSupplier).map(value -> value + " world'");
        assertThat(result.isSuccess()).isTrue();
        assertThat(result.get()).isEqualTo("This can be any method which returns: 'Hello world'");
    }

    @Test
    public void test1() throws InterruptedException {
        /**
         * 基于计数滑动窗口：最小调用数取slidingWindowSize与minimumNumberOfCalls中较小的值
         * 基于时间滑动窗口：最小调用数取minimumNumberOfCalls
         *
         * 如下配置3个请求通过，其余全部熔断
         */
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .minimumNumberOfCalls(3)
                .slidingWindowSize(10)
                .build();

        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("hello");
        CheckedFunction0<String> decoratedSupplier = CircuitBreaker.decorateCheckedSupplier(
                circuitBreaker, () -> { throw new NullPointerException(); });

        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(1);
            Try.of(decoratedSupplier).map(value -> value + " world'");
        }
    }

    @Test
    public void test2() throws InterruptedException {
        /**
         * 基于计数滑动窗口：最小调用数取slidingWindowSize与minimumNumberOfCalls中较小的值
         * 基于时间滑动窗口：最小调用数取minimumNumberOfCalls
         *
         * 如下配置2个请求通过，其余全部熔断
         */
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .minimumNumberOfCalls(3)
                .slidingWindowSize(2)
                .build();

        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("hello");
        CheckedFunction0<String> decoratedSupplier = CircuitBreaker.decorateCheckedSupplier(
                circuitBreaker, () -> { throw new NullPointerException(); });

        for (int i = 0; i < 10; i++) {
            TimeUnit.SECONDS.sleep(1);
            Try.of(decoratedSupplier).map(value -> value + " world'");
        }
    }
}
