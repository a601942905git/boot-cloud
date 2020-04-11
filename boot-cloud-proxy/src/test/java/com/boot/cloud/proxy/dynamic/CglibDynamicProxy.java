package com.boot.cloud.proxy.dynamic;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * com.boot.cloud.proxy.dynamic.CglibDynamicProxy
 *
 * @author lipeng
 * @date 2020/4/2 6:26 PM
 */
public class CglibDynamicProxy implements MethodInterceptor {

    private Object target;

    public CglibDynamicProxy(Object target) {
        this.target = target;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        System.out.println("你好，我是小王！");
        Object result = method.invoke(target, args);
        System.out.println("好的，下次家里聊！");
        return result;
    }
}
