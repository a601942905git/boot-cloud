package com.boot.cloud.rest;

import com.alibaba.fastjson.JSONObject;
import com.boot.cloud.loadbalance.CustomerRule;
import com.netflix.loadbalancer.IRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * com.boot.cloud.rest.PersonConsumer
 *
 * @author lipeng
 * @dateTime 2018/11/13 下午8:55
 */
@RestController
@RequestMapping("/persons")
@Configuration
public class PersonConsumer {

    /**
     * url前缀
     */
    public static final String PREFIX_URL = "http://spring-cloud-provider-application/";

    private final AtomicInteger atomicInteger = new AtomicInteger(1);

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    /**
     * 自定义负载策略为随机策略
     *
     * @return
     */
    @Bean
    public IRule iRule() {
        return new CustomerRule();
    }

    @GetMapping("/")
    public String listPerson() {
        RestTemplate restTemplate = getRestTemplate();

        String json = restTemplate.getForObject(PREFIX_URL + "persons/", String.class);
        return json;
    }

    @GetMapping("/id/{id}")
    public String getPersonById(@PathVariable Integer id) {
        RestTemplate restTemplate = getRestTemplate();

        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(PREFIX_URL + "persons/id/{1}", String.class, id);

        System.out.println("responseEntity.getBody()======>" + responseEntity.getBody());
        System.out.println("responseEntity.getStatusCode()======>" + responseEntity.getStatusCode());
        System.out.println("responseEntity.getStatusCodeValue()======>" + responseEntity.getStatusCodeValue());
        System.out.println("responseEntity.getHeaders()======>" + responseEntity.getHeaders());
        return responseEntity.getBody();
    }

    @GetMapping("/name/{name}")
    public String getPersonByName(@PathVariable String name) throws UnsupportedEncodingException {
        RestTemplate restTemplate = getRestTemplate();

        ResponseEntity<String> responseEntity =
                restTemplate.getForEntity(PREFIX_URL + "persons/name/{name}", String.class, name);

        System.out.println("responseEntity.getBody()======>" + responseEntity.getBody());
        System.out.println("responseEntity.getStatusCode()======>" + responseEntity.getStatusCode());
        System.out.println("responseEntity.getStatusCodeValue()======>" + responseEntity.getStatusCodeValue());
        System.out.println("responseEntity.getHeaders()======>" + responseEntity.getHeaders());
        return responseEntity.getBody();
    }

    @GetMapping("/age/{age}")
    public String getPersonByAge(@PathVariable Integer age) {
        RestTemplate restTemplate = getRestTemplate();
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(PREFIX_URL + "age?age={age}")
                .build()
                .expand(age)
                .encode();

        URI uri = uriComponents.toUri();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);
        return responseEntity.getBody();
    }

    @PostMapping("/")
    public String save() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", atomicInteger.incrementAndGet());
        jsonObject.put("name", "test" + atomicInteger.get());
        jsonObject.put("age", atomicInteger.get());

        RestTemplate restTemplate = getRestTemplate();

        String json = restTemplate.postForObject(PREFIX_URL + "persons/", jsonObject, String.class);

        return json;
    }

    @PutMapping("/")
    public void modify() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", 10001);
        jsonObject.put("name", "测试");
        jsonObject.put("age", 88);

        RestTemplate restTemplate = getRestTemplate();

        restTemplate.put(PREFIX_URL + "persons/", jsonObject);
    }

    @DeleteMapping("/{id}")
    public void modify(@PathVariable("id") Integer id) {
        RestTemplate restTemplate = getRestTemplate();

        restTemplate.delete(PREFIX_URL + "persons/{id}", id);
    }
}
