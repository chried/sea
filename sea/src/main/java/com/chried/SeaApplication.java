package com.chried;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动类.
 *
 * @author chried
 */
@SpringBootApplication(scanBasePackages = {"com.chried", "com.chried.core.resolver"})
@MapperScan("com.chried.sea.*.mapper")
@EnableRedisRepositories(value = {"com.chried.sea.redis.repository"})
@EnableTransactionManagement
public class SeaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeaApplication.class, args);
    }

}
