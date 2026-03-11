package com.shortener.url_shortener_sb.url.controller;

import com.shortener.url_shortener_sb.url.dto.ShortenRequest;
import com.shortener.url_shortener_sb.url.dto.UrlMappingDTO;
import com.shortener.url_shortener_sb.url.service.UrlMappingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {

    private final UrlMappingService urlMappingService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlMappingDTO> createShortUrl(
            @Valid @RequestBody ShortenRequest request,
            @RequestParam Long userId) {

        UrlMappingDTO dto = urlMappingService.createShortUrl(request.originalUrl(), userId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/myurls")
    public ResponseEntity<List<UrlMappingDTO>> getUserUrls(@RequestParam Long userId) {
        return ResponseEntity.ok(urlMappingService.getUrlsByUserId(userId));
    }
}