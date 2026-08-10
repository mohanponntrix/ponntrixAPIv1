package com.ponntrix.notification.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/notification")
@RestController
@Slf4j
public class NotificationController {

    @GetMapping
    public String test(){
        return "test notification controller";
    }
}
