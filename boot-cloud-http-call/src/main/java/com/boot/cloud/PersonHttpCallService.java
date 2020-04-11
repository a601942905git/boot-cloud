package com.boot.cloud;

import com.boot.cloud.proxy.SavePersonRequest;

import java.util.List;

/**
 * com.boot.cloud.PersonHttpCallService
 *
 * @author lipeng
 * @date 2020/4/3 8:16 PM
 */
public interface PersonHttpCallService {

    List<Person> listPerson();

    void savePerson(SavePersonRequest savePersonRequest);
}
