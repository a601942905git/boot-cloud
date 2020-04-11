package com.boot.cloud.proxy;

import com.boot.cloud.Person;
import com.boot.cloud.constants.RequestUrlConstant;
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
@RequestMapping("/persons1")
public class PersonHttpCallController1 {

    @Autowired
    private PersonHttpCallService1 personHttpCallService1;

    @GetMapping("/")
    public List<Person> listPerson() {
        HttpCallProxyFactory proxyFactory = new HttpCallProxyFactory(new HttpCallProxy(), personHttpCallService1);
        PersonHttpCallService1 proxy = (PersonHttpCallService1) proxyFactory.getProxy();
        return proxy.listPerson(RequestUrlConstant.LIST_PERSON_URL);
    }

    @PostMapping("/")
    public void savePerson(@RequestBody SavePersonRequest savePersonRequest) {
        HttpCallProxyFactory proxyFactory = new HttpCallProxyFactory(new HttpCallProxy(), personHttpCallService1);
        PersonHttpCallService1 proxy = (PersonHttpCallService1) proxyFactory.getProxy();
        proxy.savePerson(RequestUrlConstant.SAVE_PERSON_URL, savePersonRequest);
    }
}
