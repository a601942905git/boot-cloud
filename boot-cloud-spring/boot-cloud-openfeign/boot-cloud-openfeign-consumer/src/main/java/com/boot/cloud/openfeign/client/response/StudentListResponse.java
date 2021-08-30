package com.boot.cloud.openfeign.client.response;

import com.boot.cloud.openfeign.client.entity.Student;
import lombok.Data;

import java.util.List;

/**
 * com.boot.cloud.openfeign.client.response.StudentListResponse
 *
 * @author lipeng
 * @date 2021/8/30 7:37 PM
 */
@Data
public class StudentListResponse {

    private List<Student> studentList;
}
