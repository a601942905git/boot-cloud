package com.boot.cloud;

import com.boot.cloud.proxy.SavePersonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * com.boot.cloud.PerController
 *
 * @author lipeng
 * @date 2020/4/3 5:45 PM
 */
@RestController
@RequestMapping("/persons")
public class PersonHttpCallController {

    @Autowired
    private PersonHttpCallServiceImpl personHttpCallService;

    @GetMapping("/")
    public List<Person> listPerson() {
        return personHttpCallService.listPerson();
    }

    @PostMapping("/")
    public void getPerson(@RequestBody SavePersonRequest savePersonRequest) {
        personHttpCallService.savePerson(savePersonRequest);
    }
}
