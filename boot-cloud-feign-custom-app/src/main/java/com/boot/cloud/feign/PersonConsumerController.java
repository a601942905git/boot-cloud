package com.boot.cloud.feign;

import com.boot.cloud.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.UnknownHostException;
import java.util.List;

/**
 * com.boot.cloud.feign.PersonFeignController
 *
 * @author lipeng
 * @date 2019-07-22 18:11
 */
@RestController
@RequestMapping("/feign/consumer/persons")
public class PersonConsumerController {

    @Autowired
    private PersonClient personClient;

    @GetMapping("/")
    public List<Person> listPerson() throws UnknownHostException {
        return personClient.listPerson();
    }

    @GetMapping("/id/{id}")
    public Person getPerson(@PathVariable("id") Integer id) {
        return personClient.getPerson(id);
    }
}
