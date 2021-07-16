package com.boot.cloud.proxy.dynamic;

import com.boot.cloud.proxy.HelloService;
import com.boot.cloud.proxy.HelloServiceImpl;
import com.boot.cloud.proxy.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

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

    @Test
    public void test1() {

        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/pengli/software/idea");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        /**
         * target：生成的代理对象
         * method：被代理对象方法
         * arguments：被代理对象方法参数
         * methodProxy：代理方法
         */
        enhancer.setCallback((MethodInterceptor) (target, method, arguments, methodProxy) -> {
            System.out.println("方法执行前");
            Object result = methodProxy.invokeSuper(target, arguments);
            System.out.println("方法执行后");
            return result;
        });
        // 生成的代理类会继承被代理类，并重写父类中非final修饰的方法并在方法中通过super.xxx()调用父类方法
        UserService userService = (UserService) enhancer.create();
        userService.say();
    }
}
