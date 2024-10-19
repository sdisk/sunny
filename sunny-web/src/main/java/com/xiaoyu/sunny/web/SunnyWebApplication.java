package com.xiaoyu.sunny.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@ComponentScan(basePackages = {"com.xiaoyu.sunny.dao",
        "com.xiaoyu.sunny.service",
        "com.xiaoyu.sunny.web",
        "com.xiaoyu.sunny.common"})
@EnableAspectJAutoProxy(exposeProxy = true)
@SpringBootApplication
@EnableCaching
@Slf4j
public class SunnyWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(SunnyWebApplication.class, args);
        log.info("服务启动成功");
    }

}
