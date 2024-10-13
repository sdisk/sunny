package com.xiaoyu.sunny.web.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @Description: DruidDataSource
 * @Author XiaoYu
 * @Date 2024/9/13 13:09
 */
@Configuration
public class DruidDataSourceConfig {

    @ConfigurationProperties("spring.datasource")
    @Bean
    public DataSource dataSource() {
        DruidDataSource ds = new DruidDataSource();
        return ds;
    }
}
