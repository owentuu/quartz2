package com.example.quartz2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
public class Quartz2Application {

    public static void main(String[] args) {
        SpringApplication.run(Quartz2Application.class, args);
    }

}
