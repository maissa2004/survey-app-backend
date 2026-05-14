package com.example.appenquetes1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class AppEnquetes1Application {

    public static void main(String[] args) {
        SpringApplication.run(AppEnquetes1Application.class, args);
    }

}
