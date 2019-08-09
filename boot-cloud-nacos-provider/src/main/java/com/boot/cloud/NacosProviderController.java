package com.boot.cloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.cloud.NacosProviderController
 *
 * @author lipeng
 * @date 2019-08-08 15:07
 */
@RestController
public class NacosProviderController {

    @GetMapping("/hello")
    public String hello(String name) {
        return "hello " + name;
    }
}
