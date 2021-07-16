package com.boot.cloud;

import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * com.boot.cloud.GatewayFallbackController
 *
 * @author lipeng
 * @date 2019-09-16 17:34
 */
@RestController
public class GatewayFallbackController {

    @GetMapping("/gateway/fallback1")
    public Mono<String> fallback() {
        return Mono.just("spring cloud gateway hystrix fallback method execute!!!");
    }

    @GetMapping("/gateway/fallback2")
    public Mono<Map<String, Object>> fallback(ServerWebExchange exchange, Throwable throwable) {
        Map<String, Object> result = new HashMap<>(8);
        ServerHttpRequest request = exchange.getRequest();
        result.put("path", request.getPath().pathWithinApplication().value());
        result.put("method", request.getMethodValue());
        result.put("message", exchange.getAttribute(ServerWebExchangeUtils.CIRCUITBREAKER_EXECUTION_EXCEPTION_ATTR));
        return Mono.just(result);
    }
}
