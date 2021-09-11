package com.boot.cloud.resilience4j;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadRegistry;
import io.github.resilience4j.bulkhead.ThreadPoolBulkhead;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadConfig;
import io.github.resilience4j.bulkhead.ThreadPoolBulkheadRegistry;
import io.vavr.control.Try;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * com.boot.cloud.resilience4j.ResilienceBulkhead
 *
 * 1.隔离实现方式：
 *      1.1 信号量
 *          1.1.1 maxConcurrentCalls 最大并发数
 *          1.1.2 maxWaitDuration 获取信号量最大等待时间
 *      1.2 线程池
 *          1.2.1 maxThreadPoolSize 最大线程数
 *          1.2.2 coreThreadPoolSize 核心线程数
 *          1.2.3 queueCapacity 队列容量
 *          1.24 keepAliveDuration 线程终止前最大等待时间
 *
 *     1.3 信号量与线程池的区别：
 *          1.3.1 线程池隔离可以实现异步调用，信号量隔离只能同步调用
 *
 * @author lipeng
 * @date 2021/9/7 4:24 PM
 */
@Slf4j
public class ResilienceBulkheadTest {

    @Test
    public void semaphoreTest() throws InterruptedException {
        BulkheadConfig bulkheadConfig = BulkheadConfig.custom()
                .maxConcurrentCalls(5)
                .maxWaitDuration(Duration.ofMillis(10))
                .build();

        BulkheadRegistry bulkheadRegistry = BulkheadRegistry.of(bulkheadConfig);
        Bulkhead bulkhead = bulkheadRegistry.bulkhead("hello");
        Supplier<String> supplier = Bulkhead.decorateSupplier(bulkhead, () -> {
            try {
                log.info("execute task......");
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        });

        for (int i = 0; i < 10; i++) {
            new Thread(() -> Try.ofSupplier(supplier).map((value) -> value + "world").get()).start();
        }

        TimeUnit.SECONDS.sleep(10);
    }


    @Test
    public void threadPoolTest() throws InterruptedException {
        ThreadPoolBulkheadConfig config = ThreadPoolBulkheadConfig.custom()
                .maxThreadPoolSize(3)
                .coreThreadPoolSize(3)
                .queueCapacity(2)
                .build();

        ThreadPoolBulkheadRegistry registry = ThreadPoolBulkheadRegistry.of(config);
        ThreadPoolBulkhead bulkhead = registry.bulkhead("hello");

        for (int i = 0; i < 10; i++) {
            new Thread(() -> bulkhead.executeSupplier(() -> {
                log.info("execute task......");
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return "hello";
            })).start();
        }

        TimeUnit.SECONDS.sleep(10);
    }
}
