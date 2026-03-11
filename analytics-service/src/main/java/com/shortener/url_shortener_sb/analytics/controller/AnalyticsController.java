package com.shortener.url_shortener_sb.analytics.controller;

import com.shortener.url_shortener_sb.analytics.dto.ClickEventDTO;
import com.shortener.url_shortener_sb.analytics.dto.RecordClickEventRequest;
import com.shortener.url_shortener_sb.analytics.service.ClickEventService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@AllArgsConstructor
public class AnalyticsController {

    private final ClickEventService clickEventService;

    @PostMapping("/events")
    public ResponseEntity<Void> recordClick(@RequestBody RecordClickEventRequest request) {
        clickEventService.recordClick(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/count/link/{linkId}")
    public ResponseEntity<Map<String, Long>> getCountByLinkId(@PathVariable Long linkId) {
        return ResponseEntity.ok(Map.of("count", clickEventService.getCountByLinkId(linkId)));
    }

    @GetMapping("/count/shortUrl/{shortUrl}")
    public ResponseEntity<Map<String, Long>> getCountByShortUrl(@PathVariable String shortUrl) {
        return ResponseEntity.ok(Map.of("count", clickEventService.getCountByShortUrl(shortUrl)));
    }

    @GetMapping("/events/link/{linkId}")
    public ResponseEntity<List<ClickEventDTO>> getEventsByLinkId(
            @PathVariable Long linkId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        return ResponseEntity.ok(clickEventService.getClicksByLinkId(linkId, startDate, endDate));
    }

    @GetMapping("/events/shortUrl/{shortUrl}")
    public ResponseEntity<List<ClickEventDTO>> getEventsByShortUrl(
            @PathVariable String shortUrl,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        return ResponseEntity.ok(clickEventService.getClicksByShortUrl(shortUrl, startDate, endDate));
    }
}