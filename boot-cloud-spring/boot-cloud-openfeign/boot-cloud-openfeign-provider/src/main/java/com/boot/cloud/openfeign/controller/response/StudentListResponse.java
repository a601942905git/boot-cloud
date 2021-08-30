package com.boot.cloud.openfeign.controller.response;

import com.boot.cloud.openfeign.entity.Student;
import lombok.Data;

import java.util.List;

/**
 * com.boot.cloud.openfeign.controller.StudentListResponse
 *
 * @author lipeng
 * @date 2021/8/30 7:39 PM
 */
@Data
public class StudentListResponse {

    private List<Student> studentList;
}
