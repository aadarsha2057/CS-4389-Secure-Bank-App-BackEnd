// WebConfig
package com.utd.edu.cs4389.cometBank.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/bankingApp/**")
                .allowedOrigins("http://localhost:3000/")
                .allowedMethods("POST", "GET")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
