package com.xiaoyu.sunny.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.xiaoyu.sunny")
@SpringBootApplication
public class SunnyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunnyWebApplication.class, args);
    }

}
