## 1.前言

系统开发已经由单体服务转向为一个一个的微小服务，微小服务的好处就是每个服务只需要关心自己内部的业务，当需要相关业务数据的时候，就会面临服务调用的问题，服务调用其实也好解决，可以使用java自带的HttpURLConnection进行远程服务的调用，也可以使用HttpClient或者是OkHttp这样的第三方客户端进行远程服务调用，同样也可以使用高性能远程调用框架Dubbo。

目前比较流行的微服务技术栈，可以使用基于Netty实现的Dubbo，或者使用基于Http实现的SpringCloud，不管哪种技术，为了实现远程过程调用的便利性，使开发者只需要关注业务本身，而不需要关注调用细节，都采取了反向代理技术。反向代理技术在实现服务间透明调用起到了非常重要作用，既然重要，那么就有必要和大家一起来温习一下反向代理技术。

## 2.反向代理

### 2.1 代理的作用

代理的作用就是增强目标方法的能力，比如最常见的事务、Aop都是基于代理来实现的。当需要增强目标方法的能力，并且这些能力都是相同的，那么就可以采取代理的方式进行实现。

### 2.2 代理的分类

- 静态代理：静态代理需要为每个被代理的类创建一个代理类，当被代理的类过多的时候，就会导致代理类的增多，不便于维护。
- 动态代理：动态代理不需要为每个被代理的类创建一个代理类，只需要一个全局的代理类，在需要的时候动态生成，便于维护。

### 2.3 静态代理

![](https://wolf-heart.oss-cn-beijing.aliyuncs.com/blog/static_proxy.png)

如图可以看到被代理类实现的接口和被代理类其实就是我们开发的业务接口和业务接口实现类，在需要代理的情况下，代理类也要实现被代理类实现的接口，接下来我们来看一下代码的实现。

#### 2.3.1 被代理类实现的接口

```java
public interface HelloService {
    void hello();
}
```

#### 2.3.2 被代理类

```java
public class HelloServiceImpl implements HelloService {
    public void hello() {
        System.out.println("你吃了嘛？");
    }
}
```

#### 2.3.3 代理类

```java
public class HelloServiceStaticProxy implements HelloService {

    private HelloService helloService;

    public HelloServiceStaticProxy(HelloService helloService) {
        this.helloService = helloService;
    }

    public void hello() {
        System.out.println("你好，我是小王！");
        this.helloService.hello();
        System.out.println("好的，下次家里聊！");
    }
}
```

#### 2.3.4 测试

```java
@Test
public void staticProxy() {
    HelloServiceStaticProxy helloServiceStaticProxy = new HelloServiceStaticProxy(new HelloServiceImpl());
    helloServiceStaticProxy.hello();
}
```

```
你好，我是小王！
你吃了嘛？
好的，下次家里聊！
```

#### 2.3.5 小结

静态代理要求代理类和被代理类实现同一个接口，代理对象需要持有被代理的目标对象，在代理对象实现接口方法前后添加增强逻辑并调用目标对象方法。

### 2.4 动态代理

#### 2.4.1 动态代理实现技术

- 基于jdk实现
- 基于Cglib实现

#### 2.4.2 基于jdk实现

##### 2.4.2.1 创建代理类，实现InvocationHandler接口

```java
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
```

##### 2.4.2.2 创建代理工厂类

```java
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
```

##### 2.4.2.3 测试

```java
@Test
public void test() {
    JdkDynamicProxy jdkDynamicProxy = new JdkDynamicProxy(new HelloServiceImpl());
    JdkDynamicProxyFactory proxyFactory = new JdkDynamicProxyFactory(jdkDynamicProxy);
    HelloService proxy = (HelloService) proxyFactory.getProxy();
    proxy.hello();
}
```

```
你好，我是小王！
你吃了嘛？
好的，下次家里聊！
```

#### 2.4.3 基于cglib实现

##### 2.4.3.1 创建代理类，实现MethodInterceptor接口

```java
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
```

##### 2.4.3.2 创建代理工厂类

```java
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
```

##### 2.4.3.3 测试

```java
@Test
public void test() {
    CglibDynamicProxy cglibDynamicProxy = new CglibDynamicProxy(new HelloServiceImpl());
    CglibDynamicProxyFactory proxyFactory = new CglibDynamicProxyFactory(cglibDynamicProxy);
    HelloService proxy = (HelloService) proxyFactory.getProxy();
    proxy.hello();
}
```

```
你好，我是小王！
你吃了嘛？
好的，下次家里聊！
```

#### 2.4.4 jdk动态代理与cglib动态代理的区别

- jdk动态代理不需要引入第三方包，需要实现InvocationHandler接口，要求被代理对象必须实现接口
- cglib动态代理需要引入第三方包，需要实现MethodInterceptor接口，被代理对象可以实现接口，也可以不实现接口

### 3. 总结

今天和大家一起温习了一下代理的相关技术，代理分为：静态代理和动态代理，动态代理的实现技术有：基于jdk来实现和基于cglib来实现，cglib可以针对没有实现接口的目标对象进行代理。

代理技术是后续篇章的讲解的基石，只有掌握代理技术，才能更好的去学习、实现远程过程调用，希望你能牢牢掌握该门技术，可以让自己在后续的篇章中无任何绊脚石。