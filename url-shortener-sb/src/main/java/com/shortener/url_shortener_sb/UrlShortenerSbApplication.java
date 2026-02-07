package com.shortener.url_shortener_sb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan("com/shortener/url_shortener_sb/models")
public class UrlShortenerSbApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortenerSbApplication.class, args);
	}

}
