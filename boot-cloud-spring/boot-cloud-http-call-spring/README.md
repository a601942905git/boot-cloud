## 1.前言

本文是带你入门RPC之终极篇，主要讲解如何仿照Mybatis和借助Spring IOC来简化远程服务调用。在看该篇文章之前建议先阅读之前的三篇文章：

- [带你入门RPC之反向代理](https://juejin.im/post/5e8c0a9cf265da47dd398516)主要介绍了反向代理的实现方式，包括静态代理、动态代理，为后续进阶做准备
- [带你入门RPC之Http调用](https://juejin.im/post/5e8d2f6cf265da47af1808d2)主要介绍了使用http方式进行远程服务的调用，同时也提到该种方式存在的弊端，那就是存在重复编码的问题，会导致开发人员过多的关注远程调用的实现，降低开发效率
- [带你入门RPC之使用反向代理简化Http调用](https://juejin.im/post/5e91144e6fb9a03c4436427d)主要介绍了如何使用第一篇介绍的反向代理技术来简化http远程服务调用方式，虽然功能很简陋、也不是最佳的实现方式，却可以帮助你理解远程服务调用实现方式

在第三篇文章结尾也提到过使用反向代理实现远程服务调用存在的问题：

- 消费者接口实现类没有任何实现代码，如果存在过多的远程调用，类似的垃圾代码就会存在很多
- 每次远程服务调用还需要指定请求路径参数，是否可以借助Spring MVC来简化实现
- 开发人员每次进行远程调用还需要手动创建对象，是否可以利用Spring IOC技术来简化实现

回顾了之前存在的问题，接下就会带着你先来分析一下这些问题

## 2.问题分析

### 2.1 消费者接口实现类是否可以省略？

这个问题的答案是可以省略实现类，只需要保留接口定义即可。相信机智的你立马可以想到类似的实现，那就是你爱不释手的Mybatis框架，Mybatis框架也是基于接口来实现的。底层的原理就是，在项目启动的时候，使用扫描器去扫描指定包下面的接口，生成BeanDefinition，指定BeanDefinition对应的class，BeanDefinition的class实现FactoryBean接口，在接口实现方法getObject中实现反向代理，在使用的地方我们只需要注入的方式来实现就可以了。

### 2.2 方法中的url参数是否可以省略？

方法参数应该是跟我们业务相关、有业务意义的，不应该定义与业务相关的参数。因此我们需要将url参数从方法中移除，可是移除后就不知道远程服务的地址了，该怎么办呢？机制的你是否想到@GetMapping、@PostMapping注解？

### 2.3 是否可以采用注入方式来替代手动创建对象的方式？

使用Spring框架进行项目开发，其中有一个好处就是有需要就注入，同样的，针对远程服务的调用也可以通过注入的方式来实现。

以上针对存在的问题逐一进行了分析，也给出了相应的解决方案。但都是嘴上哔哔，并没有真枪实战，下面将带着你一起来证明"实践是检验真理的唯一标准"



## 3. 实战

### 3.1 定义开启RPC服务注解

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(RpcServiceRegistrar.class)
public @interface EnableRpcService {

    /**
     * 扫描包路径
     * @return
     */
    String[] basePackages() default {};
}
```

这种类型的注解对你来说应该不太陌生，相信你可以罗列出一箩筐这样的注解，该注解功能就是引入一个注册器，在注册其中注册bean

### 3.2 定义注册器

```java
public class RpcServiceRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableRpcService.class.getName()));
        RpcServiceClassPathScanner scanner = new RpcServiceClassPathScanner(registry);

        List<String> basePackages = new ArrayList<String>();
        for (String pkg : annotationAttributes.getStringArray("basePackages")) {
            if (StringUtils.hasText(pkg)) {
                basePackages.add(pkg);
            }
        }
        scanner.doScan(StringUtils.toStringArray(basePackages));
    }
}
```

### 3.3 定义扫描器

```java
public class RpcServiceClassPathScanner extends ClassPathBeanDefinitionScanner {

    private RpcServiceFactoryBean<?> rpcServiceFactoryBean = new RpcServiceFactoryBean<>();

    public RpcServiceClassPathScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        addIncludeFilter((metadataReader, metadataReaderFactory) -> true);
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

        if (beanDefinitions.isEmpty()) {
            logger.warn("No rpc service was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
        } else {
            processBeanDefinitions(beanDefinitions);
        }

        return beanDefinitions;
    }

    private void processBeanDefinitions(Set<BeanDefinitionHolder> beanDefinitions) {
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitions) {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();

            if (logger.isDebugEnabled()) {
                logger.debug("Creating RpcFactoryBean with name '" + holder.getBeanName()
                        + "' and '" + definition.getBeanClassName() + "' rpcInterface");
            }

            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
            definition.setBeanClass(this.rpcServiceFactoryBean.getClass());
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }
}
```

可以看到扫描器扫描了指定路径下的接口，然后注入到ioc容器中。需要注意的是bean定义指定的class为RpcServiceFactoryBean，因此容器中创建的对象都是RpcServiceFactoryBean对象

### 3.4 RpcServiceFactoryBean定义

```java
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
```

RpcServiceFactoryBean实现了FactoryBean接口，因此在注入该对象的时候会调用接口实现方法getObject()，可以看到在getObject()方法中生成了相应的代理对象，也就是说最终我们注入的是一个代理对象。

### 3.5 远程服务的定义

```java
public interface PersonRpcCallService {

    @GetMapping("http://127.0.0.1:8081/feign/persons/")
    List<Person> listPerson();

    @PostMapping("http://127.0.0.1:8081/feign/persons/")
    void savePerson(SavePersonRequest request);
}
```



### 3.6 远程服务的调用

```java
@RestController
@RequestMapping("/persons2/")
public class PersonRpcCallController2 {

    @Autowired
    private PersonRpcCallService personRpcCallService;

    @GetMapping("/")
    public List<Person> listPerson() {
        return personRpcCallService.listPerson();
    }

    @PostMapping("/")
    public void savePerson(@RequestBody SavePersonRequest savePersonRequest) {
        personRpcCallService.savePerson(savePersonRequest);
    }
}
```

可以看到此时的远程服务调用和调用内部方法没有任何的区别，在完成远程服务调用的基础上，借鉴了Mybatis的实现，融合了Spring IOC。最后再来看一下代理的实现

```java
@Slf4j
public class RpcServiceProxy implements InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        GetMapping getMappingAnnotation = AnnotationUtils.findAnnotation(method, GetMapping.class);
        PostMapping postMappingAnnotation = AnnotationUtils.findAnnotation(method, PostMapping.class);

        // 指定请求体数据类型
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        // 如果存在GetMapping注解，那么就是get请求，否则当作post请求处理
        Request request = Objects.nonNull(getMappingAnnotation) ?
                new Request.Builder()
                        .url(getMappingAnnotation.value()[0])
                        .build() :
                new Request.Builder()
                        .post(RequestBody.create(JSONObject.toJSONString(args[0]), JSON))
                        .url(postMappingAnnotation.value()[0])
                        .build();
        // 发送http请求
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("请求接口响应异常");
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

## 4.总结

到此带你入门RPC系列篇章就算是完结了，希望你看完之后，能够对远程服务调用有一个清晰的认识，为你日后阅读Dubbo、Feign源码奠定一点基石。当然，远程服务调用远非如此，比如我们还要实现重试、熔断、降级、服务发现、负载均衡等。学习之路是一个漫长的过程，愿你能享受这美好的旅程。