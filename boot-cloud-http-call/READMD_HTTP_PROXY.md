## 1. 剧情回顾

[带你入门RPC之反向代理](https://juejin.im/post/5e8c0a9cf265da47dd398516)主要回忆了反向代理实现方式，[带你入门RPC之Http调用](https://juejin.im/post/5e8d2f6cf265da47af1808d2)主要介绍了如何使用http方式调用远程服务，在第二篇结尾部分也提交到调用方式存在的缺陷，那就是远程调用逻辑对于开发者来说并不是透明的，开发者需要针对每个接口编写调用代码，增加了开发工作量以及开发成本。

## 2. 前言

既然之前的调用方式存在很大的弊端，那么此篇文章将结合第一篇讲解的代理技术来进行一定程度的优化，使得远程调用逻辑对开发者来说是透明的，开发者不需要过多的关心远程调用逻辑。

## 3. 实战

### 3.1 消费者业务接口改造

```java
public interface PersonHttpCallService1 {

    List<Person> listPerson(String url);

    void savePerson(String url, SavePersonRequest request);
}
```

> 相比之前接口多了一个参数，就是请求远程服务对应的url地址

### 3.2 消费者业务接口实现改造

```java
@Service
public class PersonHttpCallServiceImpl1 implements PersonHttpCallService1 {

    @Override
    public List<Person> listPerson(String url) {
        return null;
    }

    @Override
    public void savePerson(String url, SavePersonRequest request) {
    }
}
```

### 3.3 定义http代理类

```java
public class HttpCallProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (args.length > 2) {
            throw new IllegalArgumentException("method param length more than 2");
        }

        // 指定请求体数据类型
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        // 如果只有一个url参数，则使用get请求；如果存在2个参数，则使用post请求
        Request request = args.length == 1 ?
                new Request.Builder()
                        .url(String.valueOf(args[0]))
                        .build() :
                new Request.Builder()
                        .post(RequestBody.create(JSONObject.toJSONString(args[1]), JSON))
                        .url(String.valueOf(args[0]))
                        .build();
        // 发送http请求
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return null;
            }

            ResponseBody responseBody = response.body();
            if (Objects.isNull(responseBody)) {
                return null;
            }

            String responseContent = responseBody.string();
            // 解析返回结果
            return JSONObject.parseObject(responseContent, method.getReturnType());
        } catch (IOException e) {
            return null;
        }
    }
}
```

> 将http调用逻辑代码封装在代理类中，当调用被代理对象的时候会执行该方法，进行远程服务调用，使得远程调用逻辑对开发者来将是透明的，开发者无须关心远程调用细节实现。

> 代理中实现远程调用的方式也比较简单，如果目标方法只有一个参数，则认为是一个get请求，如果目标方法中有两个参数，则认为是一个post请求，这里只是一个简单的实现，希望各位看官不要过于纠结。

### 3.4 定义http代理工厂

```java
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
```

### 3.5 消费者控制器改造

```java
@RestController
@RequestMapping("/persons1")
public class PersonHttpCallController1 {

    @Autowired
    private PersonHttpCallService1 personHttpCallService1;

    @GetMapping("/")
    public List<Person> listPerson() {
        HttpCallProxyFactory proxyFactory = new HttpCallProxyFactory(new HttpCallProxy(), personHttpCallService1);
        PersonHttpCallService1 proxy = (PersonHttpCallService1) proxyFactory.getProxy();
        return proxy.listPerson(RequestUrlConstant.LIST_PERSON_URL);
    }

    @PostMapping("/")
    public void savePerson(@RequestBody SavePersonRequest savePersonRequest) {
        HttpCallProxyFactory proxyFactory = new HttpCallProxyFactory(new HttpCallProxy(), personHttpCallService1);
        PersonHttpCallService1 proxy = (PersonHttpCallService1) proxyFactory.getProxy();
        proxy.savePerson(RequestUrlConstant.SAVE_PERSON_URL, savePersonRequest);
    }
}
```

## 4. 总结

本文通过反向代理技术实现了远程过程调用逻辑的透明性，简化了远程调用开发工作。但是依然还存在着很多的不足，比如消费者接口实现类完全没有任何作用，是否可以省略？远程服务调用接口参数中是否可以把url参数去掉？是否可以模仿Mybatis来实现更简洁的实现？这些问题会在下一个章节进行解决，敬请期待。。。