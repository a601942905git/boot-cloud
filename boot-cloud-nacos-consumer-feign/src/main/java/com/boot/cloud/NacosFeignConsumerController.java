package com.boot.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.cloud.NacosFeignConsumerConntroller
 *
 * @author lipeng
 * @date 2019-08-12 10:36
 */
@RestController
public class NacosFeignConsumerController {

    @Autowired
    private HelloClient helloClient;

    @GetMapping("/hello")
    public String hello(@RequestParam("name") String name) {
        return helloClient.hello(name);
    }
}
