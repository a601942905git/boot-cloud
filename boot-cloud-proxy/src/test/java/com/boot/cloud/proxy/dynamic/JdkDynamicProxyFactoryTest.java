package com.boot.cloud.proxy.dynamic;

import com.boot.cloud.proxy.HelloService;
import com.boot.cloud.proxy.HelloServiceImpl;
import org.junit.jupiter.api.Test;

/**
 * com.boot.cloud.proxy.dynamic.JdkDynamicProxyFactoryTest
 *
 * @author lipeng
 * @date 2020/4/2 5:57 PM
 */
public class JdkDynamicProxyFactoryTest {

    @Test
    public void test() {
        JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(new HelloServiceImpl());
        JdkDynamicProxyFactory proxyFactory = new JdkDynamicProxyFactory(jdkDynamicProxy);
        HelloService proxy = (HelloService) proxyFactory.getProxy();
        proxy.hello();
    }
}
