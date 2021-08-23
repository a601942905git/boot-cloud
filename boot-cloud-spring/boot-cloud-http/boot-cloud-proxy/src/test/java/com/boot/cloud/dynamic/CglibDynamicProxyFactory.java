package com.boot.cloud.dynamic;

import org.springframework.cglib.proxy.Enhancer;

/**
 * com.boot.cloud.proxy.dynamic.CglibDynamicProxyFactory
 *
 * @author lipeng
 * @date 2020/4/2 6:26 PM
 */
public class CglibDynamicProxyFactory {

    private CglibDynamicProxy cglibDynamicProxy;

    public CglibDynamicProxyFactory(CglibDynamicProxy cglibDynamicProxy) {
        this.cglibDynamicProxy = cglibDynamicProxy;
    }

    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(cglibDynamicProxy.getTarget().getClass().getInterfaces());
        enhancer.setCallback(cglibDynamicProxy);
        return enhancer.create();
    }
}
