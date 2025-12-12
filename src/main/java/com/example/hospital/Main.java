package com.example.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}

@RestController
class HelloController {
    
    @GetMapping("/")
    public String home() {
        return "Вітаю! Госпітальний додаток запущено на AWS EC2!";
    }
    
    @GetMapping("/test")
    public String test() {
        return "Тестова сторінка для лабораторної роботи №5";
    }
}
