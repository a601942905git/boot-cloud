package com.boot.cloud.openfeign.controller;

import com.boot.cloud.openfeign.controller.request.StudentListRequest;
import com.boot.cloud.openfeign.controller.response.StudentListResponse;
import com.boot.cloud.openfeign.entity.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * com.boot.cloud.openfeign.controller.OpenfeignProviderController
 *
 * @author lipeng
 * @date 2021/8/23 11:27 AM
 */
@RestController
public class OpenfeignProviderController {

    @Value("${server.port}")
    private Integer port;

    @GetMapping("/index/{name}")
    public String index(@PathVariable(name = "name") String name) {
        return "openfeign service：hello " + name + " from " + port;
    }

    @PostMapping("/student/list")
    public StudentListResponse list(@RequestBody StudentListRequest studentListRequest) {
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(10001, "test1"));
        studentList.add(new Student(10002, "test2"));
        studentList.add(new Student(10003, "test3"));
        studentList.add(new Student(10004, "test4"));
        studentList.add(new Student(10005, "test5"));
        List<Student> studentFilterList = studentList.stream()
                .filter(student -> Objects.equals(student.getId(), studentListRequest.getId()))
                .filter(student -> Objects.equals(student.getName(), studentListRequest.getName()))
                .collect(Collectors.toList());
        StudentListResponse response = new StudentListResponse();
        response.setStudentList(studentFilterList);
        return response;
    }

    @GetMapping("/timeout")
    public void timeout() throws InterruptedException {
        // 用于模拟耗时任务
        TimeUnit.SECONDS.sleep(5);
    }
}
