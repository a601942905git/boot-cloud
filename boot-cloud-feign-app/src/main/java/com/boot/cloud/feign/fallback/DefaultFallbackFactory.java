package com.boot.cloud.feign.fallback;

import com.boot.cloud.Person;
import com.boot.cloud.PersonApi;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * com.boot.cloud.fallback.DefaultFallbackFactory
 * 降级工厂
 *
 * @author lipeng
 * @date 2019-07-23 16:33
 */
@Component
public class DefaultFallbackFactory implements FallbackFactory<PersonApi> {

    @Override
    public PersonApi create(Throwable throwable) {
        final Person person = new Person(99999, "大促之间服务暂不可用", 22);
        return new PersonApi() {
            @Override
            public List<Person> listPerson() throws UnknownHostException {
                return Arrays.asList(person);
            }

            @Override
            public Person getPerson(Integer id) {
                return person;
            }
        };
    }
}
