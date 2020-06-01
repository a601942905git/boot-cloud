package com.boot.cloud;

import lombok.Data;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * com.boot.cloud.Student
 *
 * @author lipeng
 * @date 2020/5/29 1:53 PM
 */
@Data
@Component
public class Student implements FactoryBean<Teacher> {

    private Integer id = 10001;

    private String name = "我是一个学生";

    @Override
    public Teacher getObject() throws Exception {
        return new Teacher();
    }

    @Override
    public Class<?> getObjectType() {
        return this.getClass();
    }
}
