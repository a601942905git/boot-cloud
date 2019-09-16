package com.boot.cloud.feign;

import com.boot.cloud.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public List<Person> listPerson(HttpServletRequest request) throws UnknownHostException {
        System.err.println("request header X-Request-Foo value：" + request.getHeader("X-Request-Foo"));
        System.err.println("request parameter foo value：" + request.getParameter("foo"));
        return personClient.listPerson();
    }

    @GetMapping("/id/{id}")
    public Person getPerson(@PathVariable("id") Integer id) {
        return personClient.getPerson(id);
    }

    @PostMapping("/")
    public void savePerson(@RequestBody Person person) {
        personClient.savePerson(person);
    }

    @PostMapping("/id/")
    public void updatePerson(@RequestBody Person person) {
        personClient.updatePerson(person);
    }
}
