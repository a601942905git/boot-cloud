package com.boot.cloud.spring;

import org.springframework.beans.factory.FactoryBean;

/**
 * com.boot.cloud.spring.RpcServiceFactoryBean
 *
 * @author lipeng
 * @date 2020/4/4 10:23 AM
 */
public class RpcServiceFactoryBean<T> implements FactoryBean<T> {

    private Class<T> rpcServiceInterface;

    public RpcServiceFactoryBean() {
    }

    public RpcServiceFactoryBean(Class<T> rpcServiceInterface) {
        this.rpcServiceInterface = rpcServiceInterface;
    }

    @Override
    public T getObject() throws Exception {
        return (T) new RpcServiceProxyFactory(rpcServiceInterface).newInstance();
    }

    @Override
    public Class<?> getObjectType() {
        return this.rpcServiceInterface;
    }
}
