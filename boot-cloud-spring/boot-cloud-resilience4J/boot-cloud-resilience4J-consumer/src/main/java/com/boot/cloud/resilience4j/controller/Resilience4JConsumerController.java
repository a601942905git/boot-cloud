package com.boot.cloud.resilience4j.controller;

import com.boot.cloud.resilience4j.client.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.cloud.resilience4j.controller.Resilience4JConsumerController
 *
 * @author lipeng
 * @date 2021/8/31 7:14 PM
 */
@RestController
public class Resilience4JConsumerController {

    @Autowired
    private TestClient testClient;

    @GetMapping("/test")
    public String test() {
        for (int i = 0; i < 10; i++) {
            testClient.test();
        }
        return "execute test method success";
    }
}
