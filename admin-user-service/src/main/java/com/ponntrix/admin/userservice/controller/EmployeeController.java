package com.ponntrix.admin.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/user/employee")
@RestController
public class EmployeeController {

    @GetMapping
    private String test(){
        return  "test emp controller";
    }


}
