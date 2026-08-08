package com.ponntrix.appointmentservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/appointment")
public class AppointmentController {

    @GetMapping
    private String test(){
        return  "appointment controller";
    }
}
