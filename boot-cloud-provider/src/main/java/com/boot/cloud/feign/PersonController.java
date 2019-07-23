package com.boot.cloud.feign;

import com.boot.cloud.Person;
import com.boot.cloud.PersonApi;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
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
    public List<Person> listPerson() throws UnknownHostException {
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

    private String getRequestUrl() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        return request.getRequestURL().toString();
    }
}
