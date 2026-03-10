package com.yyj.springmvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    
    // 配置静态资源
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
    
    // 配置跨域请求
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);
    }
    
    // 配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 这里可以添加自定义拦截器
        // registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/login", "/static/**");
    }
    
    // 配置路径映射
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 这里可以添加视图控制器，用于直接映射路径到视图
        // registry.addViewController("/").setViewName("index");
    }
}