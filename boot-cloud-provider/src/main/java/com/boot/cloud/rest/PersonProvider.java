package com.boot.cloud.rest;

import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * com.boot.cloud.rest.PersonProvider
 *
 * @author lipeng
 * @dateTime 2018/11/13 下午8:01
 */
@RestController
@RequestMapping("/persons")
public class PersonProvider {

    List<Person> personList = new ArrayList();

    @GetMapping("/")
    public List<Person> listPerson() {
        if (CollectionUtils.isEmpty(personList)) {
            personList.add(new Person(10001, "测试1", 21));
            personList.add(new Person(10002, "测试2", 22));
            personList.add(new Person(10003, "测试3", 23));
            personList.add(new Person(10004, "测试4", 24));
            personList.add(new Person(10005, "测试5", 25));
        }

        return personList;
    }

    @GetMapping("/id/{id}")
    public Person getPersonById(@PathVariable("id") Integer id) {
        Person person = personList.stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst()
                .orElseGet(Person::new);
        return person;
    }

    @GetMapping("/name/{name}")
    public Person getPersonByName(@PathVariable("name") String name) {
        Person person = personList.stream()
                .filter(p -> Objects.equals(p.getName(), name))
                .findFirst()
                .orElseGet(Person::new);
        return person;
    }

    @GetMapping("/age")
    public Person getPerson(Integer age) {
        Person person = personList.stream()
                .filter(p -> Objects.equals(p.getAge(), age))
                .findFirst()
                .orElseGet(Person::new);
        return person;
    }

    @PostMapping("/")
    public List<Person> save(@RequestBody Person person) {
        personList.add(person);
        return personList;
    }

    @PutMapping("/")
    public List<Person> modifyPerson(@RequestBody Person person) {
        Person person1 = personList.stream()
                .filter(p -> Objects.equals(p.getId(), person.getId()))
                .findFirst()
                .orElseGet(Person::new);

        personList.remove(person1);
        personList.add(person);
        return personList;
    }

    @DeleteMapping("/{id}")
    public List<Person> delete(@PathVariable Integer id) {
        Person person = personList.stream()
                .filter(p -> Objects.equals(p.getId(), id))
                .findFirst()
                .orElseGet(Person::new);

        personList.remove(person);
        return personList;
    }
}
