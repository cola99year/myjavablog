package com.cola.colablog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 15:31
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //允许8080端口访问我们后端服务器
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }
}
