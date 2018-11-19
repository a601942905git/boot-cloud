package com.boot.cloud.hystix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * com.boot.cloud.hystix.HystrixService
 *
 * @author lipeng
 * @dateTime 2018/11/19 上午10:54
 */
@Service
public class HystrixService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "error")
    public String listPersonFromProvider() {
        String json = restTemplate.getForObject(
                "http://spring-cloud-provider-application/persons/", String.class);
        return json;
    }

    public String error() {
        return "哎呀，突然打了个盹。。。请稍后重试！";
    }
}
