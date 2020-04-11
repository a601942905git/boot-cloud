package com.boot.cloud.proxy;

import org.junit.Test;

/**
 * com.boot.cloud.proxy.HelloServiceTest
 *
 * @author lipeng
 * @date 2020/4/2 5:19 PM
 */
public class StaticProxyTest {

    @Test
    public void hello() {
        HelloService helloService = new HelloServiceImpl();
        helloService.hello();
    }

    @Test
    public void staticProxy() {
        StaticProxy helloServiceStaticProxy = new StaticProxy(new HelloServiceImpl());
        helloServiceStaticProxy.hello();
    }
}
