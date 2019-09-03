package com.boot.cloud;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.net.UnknownHostException;
import java.util.List;

/**
 * com.boot.cloud.PersonFeignApi
 *
 * @author lipeng
 * @date 2019-07-22 17:04
 */
public interface PersonApi {
    /**
     * 查询列表信息
     *
     * @return 列表信息
     */
    @GetMapping("/")
    List<Person> listPerson() throws UnknownHostException;

    /**
     * 查询的单个信息
     *
     * @param id
     * @return 单个信息
     */
    @GetMapping("/id/{id}")
    Person getPerson(@PathVariable("id") Integer id);
}

