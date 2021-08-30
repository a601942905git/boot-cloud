package com.boot.cloud.openfeign.client.request;

import lombok.Data;

/**
 * com.boot.cloud.openfeign.client.request.StudentRequest
 *
 * @author lipeng
 * @date 2021/8/30 7:35 PM
 */
@Data
public class StudentListRequest {

    private Integer id;

    private String name;
}
