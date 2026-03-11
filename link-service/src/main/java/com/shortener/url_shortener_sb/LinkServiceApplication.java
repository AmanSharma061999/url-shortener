package com.shortener.url_shortener_sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
public class LinkServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LinkServiceApplication.class, args);
    }
}