package com.boot.cloud.spring;

import java.lang.reflect.Proxy;

/**
 * com.boot.cloud.spring.RpcServiceProxyFactory
 *
 * @author lipeng
 * @date 2020/4/4 10:27 AM
 */
public class RpcServiceProxyFactory<T> {

    private Class<T> rpcServiceInterface;

    public RpcServiceProxyFactory(Class<T> rpcServiceInterface) {
        this.rpcServiceInterface = rpcServiceInterface;
    }

    public T newInstance() {
        return (T) Proxy.newProxyInstance(rpcServiceInterface.getClassLoader(), new Class[]{rpcServiceInterface}, new RpcServiceProxy());
    }
}
