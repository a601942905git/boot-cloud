package com.boot.cloud.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * com.boot.cloud.HelloClient
 *
 * @author lipeng
 * @date 2019-08-12 10:37
 */
@FeignClient(name = "${service.nacos-provider.name}")
public interface HelloClient {

    @GetMapping("/hello")
    String hello(@RequestParam("name") String name);
}
