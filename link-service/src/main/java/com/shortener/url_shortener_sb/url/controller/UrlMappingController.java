package com.shortener.url_shortener_sb.url.controller;

import com.shortener.url_shortener_sb.config.jwt.CustomUserPrincipal;
import com.shortener.url_shortener_sb.url.dto.ClickEventDTO;
import com.shortener.url_shortener_sb.url.dto.ShortenRequest;
import com.shortener.url_shortener_sb.url.dto.UrlMappingDTO;
import com.shortener.url_shortener_sb.url.service.UrlMappingService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {

    private final UrlMappingService urlMappingService;

    @PostMapping("/shorten")
    public ResponseEntity<UrlMappingDTO> createShortUrl(
            @Valid @RequestBody ShortenRequest request,
            Authentication authentication) {

        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        String username = principal.getUsername();
        UrlMappingDTO dto = urlMappingService.createShortUrl(request.originalUrl(), username);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/myurls")
    public ResponseEntity<List<UrlMappingDTO>> getUserUrls(Authentication authentication) {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        String username = principal.getUsername();
        return ResponseEntity.ok(urlMappingService.getUrlsByUsername(username));
    }

    @GetMapping("/totalClicks")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksByDate(
            Authentication authentication,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        String username = principal.getUsername();

        Map<LocalDate, Long> totalClicks = urlMappingService.getTotalClicksByUsernameAndDate(username, startDate, endDate);

        return ResponseEntity.ok(totalClicks);
    }

    @GetMapping("/analytics/{shortUrl}")
    public ResponseEntity<List<ClickEventDTO>> getUrlAnalytics(@PathVariable String shortUrl,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime startDate,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime endDate,
                                                               Authentication authentication) {
        CustomUserPrincipal principal = (CustomUserPrincipal) authentication.getPrincipal();
        String username = principal.getUsername();

        return ResponseEntity.ok(urlMappingService.getClickEventsByDate(username, shortUrl, startDate, endDate));
    }
}