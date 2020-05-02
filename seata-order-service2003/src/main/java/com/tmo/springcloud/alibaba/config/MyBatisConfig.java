package com.tmo.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@MapperScan({"com.tmo.springcloud.alibaba.dao"})
public class MyBatisConfig {
}

