package com.cola.colablog.config;

import com.cola.colablog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: cola99year
 * @Date: 2022/6/26 15:31
 */
@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //允许8080端口访问我们后端服务器
        registry.addMapping("/**").allowedOrigins("http://localhost:81");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                //前端的？后端都没写也能拦截
                .addPathPatterns("/comments/create/change")
                .addPathPatterns("/articles/publish");
    }
}
