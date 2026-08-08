package com.ponntrix.lab.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lab")
@Slf4j
public class LabController {

    @GetMapping
    private String test(){
        return  "lab controller";
    }
}
