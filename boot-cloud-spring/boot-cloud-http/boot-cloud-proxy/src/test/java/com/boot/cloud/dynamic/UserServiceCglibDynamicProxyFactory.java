package com.boot.cloud.dynamic;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


/**
 * com.boot.cloud.proxy.dynamic.HelloServiceCglibDynamicProxyFactory
 *
 * @author lipeng
 * @date 2020/4/2 6:05 PM
 */
public class UserServiceCglibDynamicProxyFactory implements MethodInterceptor {

    private Object target;

    public UserServiceCglibDynamicProxyFactory(Object target) {
        this.target = target;
    }

    public Object getProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("你好，我是小王！");
        Object result = method.invoke(target, args);
        System.out.println("好的，下次家里聊！");
        return result;
    }
}
