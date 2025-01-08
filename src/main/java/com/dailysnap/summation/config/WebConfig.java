package com.dailysnap.summation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // 모든 엔드포인트에 적용
                        .allowedOrigins("http://localhost:5173") // Vue.js 개발 서버 주소 허용
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 메서드
                        .allowCredentials(true) // 쿠키 전송 허용
                        .allowedHeaders("*"); // 모든 헤더 허용
            }
        };
    }
}



