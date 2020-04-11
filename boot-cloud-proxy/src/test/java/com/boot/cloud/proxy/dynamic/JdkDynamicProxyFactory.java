package com.boot.cloud.proxy.dynamic;

import java.lang.reflect.Proxy;

/**
 * com.boot.cloud.proxy.dynamic.JdkDynamicProxyFactory
 *
 * @author lipeng
 * @date 2020/4/2 5:53 PM
 */
public class JdkDynamicProxyFactory {

    private JdkDynamicProxy jdkDynamicProxy;

    public JdkDynamicProxyFactory(JdkDynamicProxy helloServiceJdkDynamicProxy) {
        this.jdkDynamicProxy = helloServiceJdkDynamicProxy;
    }

    public Object getProxy() {
        Object target = jdkDynamicProxy.getTarget();
        return Proxy.newProxyInstance(jdkDynamicProxy.getClass().getClassLoader(), target.getClass().getInterfaces(), jdkDynamicProxy);
    }

}
