package com.boot.cloud.openfeign.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.cloud.openfeign.controller.OpenfeignProviderController
 *
 * @author lipeng
 * @date 2021/8/23 11:27 AM
 */
@RestController
public class OpenfeignProviderController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/{name}")
    public String index(@PathVariable(name = "name") String name) {
        return "openfeign service：hello " + name + " from " + port;
    }

}
