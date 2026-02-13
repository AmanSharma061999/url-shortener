package com.shortener.url_shortener_sb.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${frontend.url}")
    private String frontendUrl;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(frontendUrl)
                        .allowedMethods("GET", "POST","PUT","PATCH","DELETE","OPTIONS")
                        .allowedHeaders("*")
                        .exposedHeaders("X-Trace-Id")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
}


/*
What WebConfig handles
WebConfig is about CORS (Cross-Origin Resource Sharing).

It answers the browser’s questions:
    * “Am I allowed to call this backend from this frontend?”
    * “Am I allowed to send cookies?”
    * “Can I read certain response headers?”

Things ONLY WebConfig does
    * Allowed frontend origins (http://localhost:5173)
    * Allowed HTTP methods
    * Allowed headers
    * Whether cookies are allowed (allowCredentials)
    * Which response headers frontend can read

 */
