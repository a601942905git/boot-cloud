package com.boot.cloud.feign;

import com.boot.cloud.Person;
import com.boot.cloud.PersonApi;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * com.boot.cloud.feign.PersonController
 *
 * @author lipeng
 * @date 2019-07-22 18:03
 */
@RestController
public class PersonController implements PersonApi {

    List<Person> personList = new ArrayList();

    @Override
    public List<Person> listPerson() {
        if (CollectionUtils.isEmpty(personList)) {
            personList.add(new Person(10006, "测试6", 21));
            personList.add(new Person(10007, "测试7", 22));
            personList.add(new Person(10008, "测试8", 23));
            personList.add(new Person(10009, "测试9", 24));
            personList.add(new Person(100010, "测试10", 25));
        }
        return personList;
    }
}
