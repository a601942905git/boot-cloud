## 1. 前言

针对服务化应用，通常会存在服务之间的相互调用，比如订单系统会调用商品系统、促销系统、库存系统等。调用的协议有应用层的http协议、传出层的tcp协议，http方式可以使用jdk自带的HttpURLConnection、第三方提供的http框架，tcp方式可以使用dubbo，本文主要使用OkHttp来实现远程服务的调用。

## 2.实现功能

本文主要讲述定义一个服务，该服务提供查询用户列表、保存用户信息两个接口；再定义一个服务消费者，服务消费者通过http方式调用服务提供者提供的服务，从而实现查看用户列表、新增用户两个功能。

## 3. 服务提供者

### 3.1 服务接口

```java
public interface PersonApi {
    /**
     * 查询列表信息
     *
     * @return 列表信息
     */
    @GetMapping("/")
    List<Person> listPerson() throws UnknownHostException; 

    @PostMapping("/")
    void savePerson(@RequestBody Person person);
}
```

### 3.2 服务接口实现

```java
@RestController
@RequestMapping("/feign/persons")
public class PersonController implements PersonApi {

    List<Person> personList = new ArrayList<>();

    @Override
    public List<Person> listPerson() throws UnknownHostException {
        if (CollectionUtils.isEmpty(personList)) {
            personList.add(new Person(10006, requestUrl + "测试6", 21));
            personList.add(new Person(10007, requestUrl + "测试7", 22));
            personList.add(new Person(10008, requestUrl + "测试8", 23));
            personList.add(new Person(10009, requestUrl + "测试9", 24));
            personList.add(new Person(100010, requestUrl + "测试10", 25));
        }
        return personList;
    }
    
    @Override
    public void savePerson(@RequestBody Person person) {
        personList.add(person);
    }
}
```

## 4. 服务消费者

### 4.1 调用服务url定义

```java
public class RequestUrlConstant {

    public static final String LIST_PERSON_URL = "http://127.0.0.1:8081/feign/persons/";

    public static final String SAVE_PERSON_URL = "http://127.0.0.1:8081/feign/persons/";
}
```

### 4.2 消费者业务接口

```java
public interface PersonHttpCallService {

    List<Person> listPerson();

    void savePerson(SavePersonRequest savePersonRequest);
}
```

### 4.3 消费者业务实现

```java
@Service
public class PersonHttpCallServiceImpl implements PersonHttpCallService {

    public List<Person> listPerson() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(RequestUrlConstant.LIST_PERSON_URL).build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                return null;
            }
            String responseBody = response.body().string();
            return JSONObject.parseArray(responseBody, Person.class);
        } catch (IOException e) {
            return null;
        }
    }

    public void savePerson(SavePersonRequest savePersonRequest) {
        OkHttpClient client = new OkHttpClient();
        final MediaType JSON = MediaType.get("application/json; charset=utf-8");
        Request request = new Request.Builder()
                .post(RequestBody.create(JSONObject.toJSONString(savePersonRequest), JSON))
                .url(RequestUrlConstant.SAVE_PERSON_URL)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {

            }
        } catch (IOException e) {

        }
    }
}
```

### 4.4 消费者控制器

```java
@RestController
@RequestMapping("/persons")
public class PersonHttpCallController {

    @Autowired
    private PersonHttpCallServiceImpl personHttpCallService;

    @GetMapping("/")
    public List<Person> listPerson() {
        return personHttpCallService.listPerson();
    }

    @PostMapping("/")
    public void getPerson(@RequestBody SavePersonRequest savePersonRequest) {
        personHttpCallService.savePerson(savePersonRequest);
    }
}
```

### 4.5 新增用户

#### 4.5.1 请求url

http://localhost:8096/persons/

#### 4.5.2 请求body

```json
{
	"id":"998888777",
	"name":"新增一个用户",
	"age":"22"
}
```

### 4.6 查看用户列表信息

http://localhost:8096/persons/

#### 4.6.1 用户列表接口返回

```json
[{"id":10006,"name":"http://127.0.0.1:8081/feign/persons/测试6","age":21},{"id":10007,"name":"http://127.0.0.1:8081/feign/persons/测试7","age":22},{"id":10008,"name":"http://127.0.0.1:8081/feign/persons/测试8","age":23},{"id":10009,"name":"http://127.0.0.1:8081/feign/persons/测试9","age":24},{"id":100010,"name":"http://127.0.0.1:8081/feign/persons/测试10","age":25},{"id":998888777,"name":"新增一个用户","age":22}]
```

## 5.总结

本文主要讲述了服务消费者通过http的方式调用服务提供者提供的服务，可能你会觉得这篇文章没有什么有价值的内容，完全可以使用Spring Cloud中的feign来实现远程调用，方便、简单、快捷、代码简洁。对，你说的没错，使用Spring Cloud提供的feign固然很简捷，但是你是否理解其中的原理？是否可以动手实现一个简化版的feign？带你入门rpc系列就一步一步带你实现一个简化版的feign，让你理解其中的原理。理解原理不管对个人的成长还是面试都是一件不错的事情，你说呢？

## 6.预告

通过如上示例相信你已经厌恶这种调用方式，每当需要调用一个服务，就要写一大坨与业务毫无关系的http调用代码，极大降低了自己的开发效率，下一篇就带着你使用反向代理来解决重复代码问题。