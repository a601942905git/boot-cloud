package com.boot.cloud.feign.fallback;

import com.boot.cloud.Person;
import com.boot.cloud.feign.PersonClient;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

/**
 * com.boot.cloud.fallback.DefaultFallback
 * 此组件指定如下value的原因：
 * 指定此value，在查找映射信息的时候会被排除掉
 * 如果不指定value，那么就会出现重复的requestMapping，因为DefaultFallback和PersonClient都属于PersonApi接口
 *
 * @author lipeng
 * @date 2019-07-23 17:42
 */
@Component
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

    @Override
    public void savePerson(Person person) {
        return;
    }

    @Override
    public void updatePerson(Person person) {
        return;
    }
}
