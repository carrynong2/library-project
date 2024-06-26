package com.carrynong.springbootlibrary.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/books")
                .allowedOrigins("*")
                .allowedMethods("GET")
                .maxAge(3600);

        registry.addMapping("/api/reviews")
                .allowedOrigins("*")
                .allowedMethods("GET")
                .maxAge(3600);

        registry.addMapping("/api/messages")
                .allowedOrigins("*")
                .allowedMethods("GET")
                .maxAge(3600);
    }
}