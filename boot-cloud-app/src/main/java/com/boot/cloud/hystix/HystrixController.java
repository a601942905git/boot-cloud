package com.boot.cloud.hystix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.cloud.hystix.HystrixController
 *
 * @author lipeng
 * @dateTime 2018/11/19 上午10:48
 */
@RestController
@RequestMapping("/hystrix")
public class HystrixController {

    @Autowired
    private HystrixService hystrixService;

    @GetMapping("/persons")
    public String listPerson() {
        return hystrixService.listPersonFromProvider();
    }
}
