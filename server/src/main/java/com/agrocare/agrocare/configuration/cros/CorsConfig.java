package com.agrocare.agrocare.configuration.cros;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3003", "http://localhost:3000", "https://agrocare-vzq2.onrender.com", "http://localhost", "http://52.53.246.18", "http://localhost:3000", "http://52.53.246.18:3000") // Allow requests from this origin
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Allowed HTTP methods
                .allowedHeaders("*") // Allowed headers, you can specify specific headers if needed
                .allowCredentials(true); // Allow including cookies in CORS requests if required
    }
}
