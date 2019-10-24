package com.chried.sea.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分页配置.
 *
 * @author chried
 */
@Configuration
public class PaginationConfig {

    /**
     * 分页插件配置.
     *
     * @return 分页.
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        // 设置方言类型
        page.setDialectType("mysql");
        return page;
    }
}
