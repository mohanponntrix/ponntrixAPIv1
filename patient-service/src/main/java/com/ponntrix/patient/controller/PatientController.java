package com.ponntrix.patient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patient")
@Slf4j
public class PatientController {

    @GetMapping
    public String test(){
        return "test patient controller";
    }
}
