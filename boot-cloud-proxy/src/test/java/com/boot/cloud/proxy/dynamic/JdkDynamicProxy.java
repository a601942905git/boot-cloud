package com.boot.cloud.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * com.boot.cloud.proxy.dynamic.JdkDynamicProxy
 *
 * @author lipeng
 * @date 2020/4/7 10:53 AM
 */
public class JdkDynamicProxy implements InvocationHandler {

    private Object target;

    public JdkDynamicProxy(Object target) {
        this.target = target;
    }

    public Object getTarget() {
        return target;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("你好，我是小王！");
        Object result = method.invoke(target, args);
        System.out.println("好的，下次家里聊！");
        return result;
    }
}
