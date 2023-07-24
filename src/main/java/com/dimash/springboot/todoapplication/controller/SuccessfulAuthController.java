package com.dimash.springboot.todoapplication.controller;

import com.dimash.springboot.todoapplication.response.HelloResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class SuccessfulAuthController {
    @GetMapping
    public HelloResponse hello() {
        return new HelloResponse("Welcome to your own Todo List!");
    }
}
