package com.shortener.url_shortener_sb.controller;

import com.shortener.url_shortener_sb.models.UrlMapping;
import com.shortener.url_shortener_sb.service.UrlMappingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedirectController {

    private UrlMappingService urlMappingService;
    public RedirectController(UrlMappingService urlMappingService) {
        this.urlMappingService=urlMappingService;
    }

    @GetMapping("/s/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        UrlMapping urlMapping = urlMappingService.getOriginalUrl(shortUrl);

        if (urlMapping == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.status(302)
                .header(HttpHeaders.LOCATION, urlMapping.getOriginalUrl())
                .build();
    }
}
