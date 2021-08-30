package com.boot.cloud.openfeign.controller;

import com.boot.cloud.openfeign.client.IndexClient;
import com.boot.cloud.openfeign.client.request.StudentListRequest;
import com.boot.cloud.openfeign.client.response.StudentListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.boot.cloud.openfeign.controller.OpenfeignConsumerController
 *
 * @author lipeng
 * @date 2021/8/23 11:31 AM
 */
@RestController
public class OpenfeignConsumerController {

    @Autowired
    private IndexClient indexClient;

    @GetMapping("/index/{name}")
    public String index(@PathVariable(name = "name") String name) {
        return indexClient.index(name);
    }

    @PostMapping("/student/list")
    public StudentListResponse list(@RequestBody StudentListRequest studentRequest) {
        return indexClient.list(studentRequest);
    }
}
