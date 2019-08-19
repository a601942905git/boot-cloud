package com.sentinel.example.controller;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.sentinel.example.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.sentinel.example.controller.TestController
 *
 * @author lipeng
 * @date 2019-08-19 11:47
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/block")
    public String block(@RequestParam(value = "name", required = false) String name) throws BlockException {
        return testService.blockHandler(name);
    }

    @RequestMapping("/hello")
    public String hello(@RequestParam(value = "name", required = false) String name) {
        return testService.hello(name);
    }

    @RequestMapping("/hi")
    public String hi(@RequestParam(value = "name", required = false) String name) {
        return testService.hi(name);
    }

    @RequestMapping("/test")
    public String test(@RequestParam(value = "name", required = false) String name) {
        return testService.test(name);
    }

    @RequestMapping("/ignore")
    public String ignore(@RequestParam(value = "name", required = false) String name) {
        return testService.ignore(name);
    }
}
