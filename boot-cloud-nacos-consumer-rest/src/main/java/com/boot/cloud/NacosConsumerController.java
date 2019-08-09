package com.boot.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.cloud.NacosConsumerController
 *
 * @author lipeng
 * @date 2019-08-08 15:02
 */
@RestController
public class NacosConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(String name) {
        return restTemplate.getForObject("http://boot-cloud-nacos-provider/hello?name=" + name, String.class);
    }
}
