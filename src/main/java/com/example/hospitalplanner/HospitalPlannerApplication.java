package com.example.hospitalplanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.hospitalplanner")
@RestController
public class HospitalPlannerApplication {
//http://localhost:9087/
    public static void main(String[] args) {
        SpringApplication.run(HospitalPlannerApplication.class, args);
    }
}
