package com.shortener.url_shortener_sb.controller;

import com.shortener.url_shortener_sb.dtos.ClickEventDTO;
import com.shortener.url_shortener_sb.dtos.UrlMappingDTO;
import com.shortener.url_shortener_sb.models.User;
import com.shortener.url_shortener_sb.service.UrlMappingService;
import com.shortener.url_shortener_sb.service.UserService;
import lombok.AllArgsConstructor;
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

    private UrlMappingService urlMappingService;
    private UserService userService;

    @PostMapping("/shorten")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<UrlMappingDTO> createShortUrl(@RequestBody Map<String, String> request,
                                                         Principal principal) {
        String OriginalUrl = request.get("originalUrl");
        User user = userService.findByUsername(principal.getName());
        UrlMappingDTO urlMappingDTO = urlMappingService.createShortUrl(OriginalUrl,user);
        return ResponseEntity.ok(urlMappingDTO);

    }

    @GetMapping("/myurls")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<UrlMappingDTO>> getUserUrls(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<UrlMappingDTO> urls = urlMappingService.getUrlsByUser(user);
        return ResponseEntity.ok(urls);
    }

    @GetMapping("/totalClicks")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<Map<LocalDate, Long>> getTotalClicksBydate(Principal principal,
                                                                     @RequestParam("startDate") String startDate,
                                                                     @RequestParam("endDate") String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
        User user = userService.findByUsername(principal.getName());
        LocalDate start = LocalDate.parse(startDate,formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        Map<LocalDate, Long> totalClicks = urlMappingService.getTotalClicksByUserAndDate(user, start, end);
        return ResponseEntity.ok(totalClicks);
    }

    @GetMapping("/analytics/{shortUrl}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<List<ClickEventDTO>> getUrlAnalytics(@PathVariable String shortUrl,
                                                          @RequestParam("startDate") String startDate,
                                                          @RequestParam("endDate") String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime start = LocalDateTime.parse(startDate,formatter);
        LocalDateTime end = LocalDateTime.parse(endDate,formatter);
        List<ClickEventDTO> clickEventDTOS = urlMappingService.getClickEventsByDate(shortUrl,start,end);
        return ResponseEntity.ok(clickEventDTOS);
    }
}
