package com.boot.cloud.controller;

import com.boot.cloud.entity.Person;
import com.boot.cloud.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * com.boot.cloud.controller.PersonController
 *
 * @author lipeng
 * @date 2021/7/16 4:28 PM
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public List<Person> list() throws IOException {
        return personService.list();
    }
}
