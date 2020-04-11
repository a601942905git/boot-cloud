package com.boot.cloud.spring;

import com.boot.cloud.Person;
import com.boot.cloud.proxy.SavePersonRequest;
import com.boot.cloud.spring.third.service.PersonRpcCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.boot.cloud.spring.PersonRpcCallController2
 *
 * @author lipeng
 * @date 2020/4/5 10:19 AM
 */
@RestController
@RequestMapping("/persons2/")
public class PersonRpcCallController2 {

    @Autowired
    private PersonRpcCallService personRpcCallService;

    @GetMapping("/")
    public List<Person> listPerson() {
        return personRpcCallService.listPerson();
    }

    @PostMapping("/")
    public void savePerson(@RequestBody SavePersonRequest savePersonRequest) {
        personRpcCallService.savePerson(savePersonRequest);
    }
}
