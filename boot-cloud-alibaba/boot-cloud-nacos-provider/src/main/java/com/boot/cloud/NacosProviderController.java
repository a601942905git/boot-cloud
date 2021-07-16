package com.boot.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.cloud.NacosProviderController
 *
 * @author lipeng
 * @date 2019-08-08 15:07
 */
@RestController
public class NacosProviderController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return "hello " + name + "，response from " + port;
    }
}
