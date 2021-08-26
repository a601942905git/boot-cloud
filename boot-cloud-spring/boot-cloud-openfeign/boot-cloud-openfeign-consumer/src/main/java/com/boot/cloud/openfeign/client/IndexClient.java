package com.boot.cloud.openfeign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * com.boot.cloud.openfeign.client.OpenfeignClient
 *
 * @author lipeng
 * @date 2021/8/23 11:32 AM
 */
@FeignClient(value = "openfeign-provider-service")
public interface IndexClient {

    @GetMapping("/index/{name}")
    String index(@PathVariable(name = "name") String name);
}
