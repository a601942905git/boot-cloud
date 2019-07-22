package com.boot.cloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * com.boot.cloud.PersonFeignApi
 *
 * @author lipeng
 * @date 2019-07-22 17:04
 */
@RequestMapping("/feign/persons")
public interface PersonApi {

    @GetMapping("/")
    List<Person> listPerson();
}
