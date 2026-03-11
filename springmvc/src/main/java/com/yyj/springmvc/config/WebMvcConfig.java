package com.yyj.springmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

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
        // 注册JWT拦截器
        registry.addInterceptor(new com.yyj.springmvc.interceptor.JWTInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/static/**");
    }
    
    // 配置路径映射
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 这里可以添加视图控制器，用于直接映射路径到视图
        // registry.addViewController("/").setViewName("index");
    }
    
    // 配置消息转换器
    // @Override
    // public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    //     // 先添加默认的消息转换器
    //     WebMvcConfigurer.super.configureMessageConverters(converters);
    //     
    //     // 确保Jackson消息转换器被添加
    //     MappingJackson2HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();
    //     converters.add(jacksonConverter);
    // }
}