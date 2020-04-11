package com.boot.cloud.proxy.dynamic;

import com.boot.cloud.proxy.HelloService;
import com.boot.cloud.proxy.HelloServiceImpl;
import org.junit.Test;

/**
 * com.boot.cloud.proxy.dynamic.CglibDynamicProxyFactoryTest
 *
 * @author lipeng
 * @date 2020/4/2 6:28 PM
 */
public class CglibDynamicProxyFactoryTest {

    @Test
    public void test() {
        CglibDynamicProxy cglibDynamicProxy = new CglibDynamicProxy(new HelloServiceImpl());
        CglibDynamicProxyFactory proxyFactory = new CglibDynamicProxyFactory(cglibDynamicProxy);
        HelloService proxy = (HelloService) proxyFactory.getProxy();
        proxy.hello();
    }
}
