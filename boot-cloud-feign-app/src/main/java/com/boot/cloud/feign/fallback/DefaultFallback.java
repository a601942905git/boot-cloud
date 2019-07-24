package com.boot.cloud.feign.fallback;

import com.boot.cloud.Person;
import com.boot.cloud.feign.PersonClient;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * com.boot.cloud.fallback.DefaultFallback
 *
 * @author lipeng
 * @date 2019-07-23 17:42
 */
@Component(value = "scopedTarget.defaultFallback")
public class DefaultFallback implements PersonClient {
    final Person person = new Person(88888, "大促之间服务暂不可用!!!", 22);

    @Override
    public List<Person> listPerson() throws UnknownHostException {
        return Arrays.asList(person);
    }

    @Override
    public Person getPerson(Integer id) {
        return person;
    }

}
