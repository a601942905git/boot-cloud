package com.boot.cloud.spring.third.service;

import com.boot.cloud.spring.Person;
import com.boot.cloud.spring.SavePersonRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * com.boot.cloud.spring.third.service.PersonRpcCallService
 *
 * @author lipeng
 * @date 2020/4/5 10:04 AM
 */

public interface PersonRpcCallService {

    @GetMapping("http://127.0.0.1:8081/feign/persons/")
    List<Person> listPerson();

    @PostMapping("http://127.0.0.1:8081/feign/persons/")
    void savePerson(SavePersonRequest request);
}
