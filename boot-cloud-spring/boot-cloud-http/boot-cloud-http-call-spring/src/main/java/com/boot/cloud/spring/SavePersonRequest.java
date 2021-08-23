package com.boot.cloud.spring;

import lombok.Data;

/**
 * com.boot.cloud.proxy.GetPersonRequest
 *
 * @author lipeng
 * @date 2020/4/3 9:24 PM
 */
@Data
public class SavePersonRequest {

    private Integer id;

    private String name;

    private Integer age;
}
