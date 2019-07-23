package com.boot.cloud;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.UnknownHostException;
import java.util.List;

/**
 * com.boot.cloud.PersonFeignApi
 *
 * @author lipeng
 * @date 2019-07-22 17:04
 */
@FeignClient("spring-cloud-provider-application")
@RequestMapping("/feign/persons")
public interface PersonApi {

    /**
     * 查询列表信息
     *
     * @return 列表信息
     */
    @GetMapping("/")
    List<Person> listPerson() throws UnknownHostException;
}
