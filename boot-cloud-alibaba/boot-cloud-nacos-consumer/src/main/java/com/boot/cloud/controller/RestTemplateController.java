package com.boot.cloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.cloud.controller.RestTempController
 *
 * @author lipeng
 * @date 2021/7/16 5:45 PM
 */
@RestController
@RequestMapping("/rest")
public class RestTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name") String name) {
        return restTemplate.getForObject("http://boot-cloud-nacos-provider/hello?name=" + name, String.class);
    }
}
