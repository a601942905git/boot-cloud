package com.boot.cloud.openfeign.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * com.boot.cloud.openfeign.entity.Student
 *
 * @author lipeng
 * @date 2021/8/30 7:40 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    private Integer id;

    private String name;
}
