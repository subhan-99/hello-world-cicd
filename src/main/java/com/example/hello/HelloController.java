package com.example.hello;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/")
    public String hello() {
        return "Hello World! Spring Boot is deployed on Tomcat 10.1.";
    }

    @GetMapping("/health")
    public String health() {
        return "UP";
    }
}
