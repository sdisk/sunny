package com.xiaoyu.sunny.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan(basePackages = {"com.xiaoyu.sunny.dao",
        "com.xiaoyu.sunny.service",
        "com.xiaoyu.sunny.web",
        "com.xiaoyu.sunny.common"})
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
public class SunnyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunnyWebApplication.class, args);
    }

}
