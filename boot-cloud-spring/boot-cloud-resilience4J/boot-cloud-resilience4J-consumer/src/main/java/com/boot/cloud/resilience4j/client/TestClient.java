package com.boot.cloud.resilience4j.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * com.boot.cloud.resilience4j.client.TestClient
 *
 * @author lipeng
 * @date 2021/8/31 7:17 PM
 */
@FeignClient(value = "resilience4j-provider-service")
public interface TestClient {

    @GetMapping("/test")
    String test();

    @GetMapping("/timeout")
    String timeout();
}
