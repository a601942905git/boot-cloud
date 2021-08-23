package com.boot.cloud.dynamic;

import com.boot.cloud.UserService;
import org.junit.jupiter.api.Test;

/**
 * com.boot.cloud.proxy.dynamic.HelloServiceCglibDynamicProxyFactoryTest
 *
 * @author lipeng
 * @date 2020/4/2 6:10 PM
 */
public class UserServiceCglibDynamicProxyFactoryTest {

    @Test
    public void test() {
        UserServiceCglibDynamicProxyFactory proxyFactory = new UserServiceCglibDynamicProxyFactory(new UserService());
        UserService proxy = (UserService) proxyFactory.getProxy();
        proxy.say();
    }
}
