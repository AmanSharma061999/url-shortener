package com.shortener.url_shortener_sb.controller;

import com.shortener.url_shortener_sb.dtos.ClickEventDTO;
import com.shortener.url_shortener_sb.dtos.UrlMappingDTO;
import com.shortener.url_shortener_sb.dtos.requests.ShortenRequest;
import com.shortener.url_shortener_sb.models.User;
import com.shortener.url_shortener_sb.service.UrlMappingService;
import com.shortener.url_shortener_sb.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/urls")
@AllArgsConstructor
public class UrlMappingController {

    private final UrlMappingService urlMappingService;
    // private UserService userService;

    @PostMapping("/shorten")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UrlMappingDTO> createShortUrl(@Valid @RequestBody ShortenRequest request,
                                                        Principal principal) {

        UrlMappingDTO urlMappingDTO = urlMappingService.createShortUrl(request.originalUrl(), principal.getName());
        return ResponseEntity.ok(urlMappingDTO);

    }

    @GetMapping("/myurls")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<UrlMappingDTO>> getUserUrls(Principal principal) {
        return ResponseEntity.ok(urlMappingService.getUrlsByUsername(principal.getName()));
    }

    @GetMapping("/totalClicks")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksBydate(Principal principal,
                                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Map<LocalDate, Long> totalClicks = urlMappingService.getTotalClicksByUsernameAndDate(principal.getName(), startDate, endDate);
        return ResponseEntity.ok(totalClicks);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<ClickEventDTO>> getUrlAnalytics(@PathVariable String shortUrl,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(urlMappingService.getClickEventsByDate(shortUrl,startDate,endDate));
    }
}
