package com.boot.cloud.proxy;

import java.lang.reflect.Proxy;

/**
 * com.boot.cloud.HttpCallProxyFactory
 *
 * @author lipeng
 * @date 2020/4/3 8:22 PM
 */
public class HttpCallProxyFactory {

    private HttpCallProxy httpCallProxy;

    private Object target;

    public HttpCallProxyFactory(HttpCallProxy httpCallProxy, Object target) {
        this.httpCallProxy = httpCallProxy;
        this.target = target;
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), httpCallProxy);
    }
}
