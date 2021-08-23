package com.boot.cloud.spring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * com.boot.cloud.rest.Person
 *
 * @author lipeng
 * @dateTime 2018/11/13 下午7:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Person implements Serializable {

    private static final long serialVersionUID = -518708502078052427L;

    private Integer id;

    private String name;

    private Integer age;
}
