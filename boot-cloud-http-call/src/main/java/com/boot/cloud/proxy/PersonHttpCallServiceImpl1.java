package com.boot.cloud.proxy;

import com.boot.cloud.Person;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.boot.cloud.PersonHttpCallServiceImpl1
 *
 * @author lipeng
 * @date 2020/4/3 8:51 PM
 */
@Service
public class PersonHttpCallServiceImpl1 implements PersonHttpCallService1 {

    @Override
    public List<Person> listPerson(String url) {
        return null;
    }

    @Override
    public void savePerson(String url, SavePersonRequest request) {
    }
}
