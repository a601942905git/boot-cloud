package com.boot.cloud.service;

import com.boot.cloud.client.PersonClient;
import com.boot.cloud.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * com.boot.cloud.service.PersonService
 *
 * @author lipeng
 * @date 2021/7/16 4:27 PM
 */
@Service
public class PersonService {

    @Autowired
    private PersonClient personClient;

    public List<Person> list() throws IOException {
        return personClient.list().execute().body();
    }
}
