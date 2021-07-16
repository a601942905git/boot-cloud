package com.boot.cloud;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * com.boot.cloud.RemorteAddressKeyResolver
 * 限流key解析器
 *
 * @author lipeng
 * @date 2019-09-18 19:52
 */
public class RemoteAddressKeyResolver implements KeyResolver {

    public static final String BEAN_NAME = "remoteAddressKeyResolver";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        // 基于ip进行限流
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }
}
