package com.boot.cloud.feign;

import com.boot.cloud.Person;
import com.boot.cloud.PersonApi;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * com.boot.cloud.feign.PersonController
 *
 * @author lipeng
 * @date 2019-07-22 18:03
 */
@RestController
@RequestMapping("/feign/persons")
public class PersonController implements PersonApi {

    List<Person> personList = new ArrayList();

    @Override
    public List<Person> listPerson() throws UnknownHostException {
        isNeedBlock(false);
        System.err.println("======personList======" + personList);
        if (CollectionUtils.isEmpty(personList)) {
            String requestUrl = getRequestUrl();
            personList.add(new Person(10006, requestUrl + "测试6", 21));
            personList.add(new Person(10007, requestUrl + "测试7", 22));
            personList.add(new Person(10008, requestUrl + "测试8", 23));
            personList.add(new Person(10009, requestUrl + "测试9", 24));
            personList.add(new Person(100010, requestUrl + "测试10", 25));
        }
        return personList;
    }

    private void isNeedBlock(boolean b) {
        if (!b) {
            return;
        }
        // 用来模拟耗时操作，测试客户端重试机制
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Person getPerson(Integer id) {
        Person person = personList.stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst()
                .orElseGet(Person::new);
        return person;
    }

    @Override
    public void savePerson(@RequestBody Person person) {
        personList.add(person);
    }

    @Override
    public void updatePerson(@RequestBody Person person) {
        personList.add(person);
    }

    private String getRequestUrl() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getRequestURL().toString();
    }
}
