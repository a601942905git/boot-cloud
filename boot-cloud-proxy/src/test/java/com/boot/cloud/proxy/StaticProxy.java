package com.boot.cloud.proxy;

/**
 * com.boot.cloud.proxy.HelloStaticProxy
 *
 * @author lipeng
 * @date 2020/4/2 5:21 PM
 */
public class StaticProxy implements HelloService {

    private HelloService helloService;

    public StaticProxy(HelloService helloService) {
        this.helloService = helloService;
    }

    public void hello() {
        System.out.println("你好，我是小王！");
        this.helloService.hello();
        System.out.println("好的，下次家里聊！");
    }
}
