package com.boot.cloud;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * com.boot.cloud.ExampleApplication
 *
 * @author lipeng
 * @date 2020/5/29 1:50 PM
 */
@SpringBootApplication
@Log4j2
public class ExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ExampleApplication.class, args);
        log.info("get bean by name is factory bean：{}",
                applicationContext.getBean("student") instanceof FactoryBean);
        log.info("get bean by factory bean prefix is factory bean：{}",
                applicationContext.getBean(BeanFactory.FACTORY_BEAN_PREFIX + "student") instanceof FactoryBean);
    }
}
