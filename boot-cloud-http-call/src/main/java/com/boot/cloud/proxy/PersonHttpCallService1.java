package com.boot.cloud.proxy;

import com.boot.cloud.Person;

import java.util.List;

/**
 * com.boot.cloud.PersonHttpCallService
 *
 * @author lipeng
 * @date 2020/4/3 8:16 PM
 */
public interface PersonHttpCallService1 {

    List<Person> listPerson(String url);

    void savePerson(String url, SavePersonRequest request);
}
