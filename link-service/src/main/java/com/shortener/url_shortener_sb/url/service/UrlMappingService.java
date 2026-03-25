package com.shortener.url_shortener_sb.url.service;

import com.shortener.url_shortener_sb.url.dto.UrlMappingDTO;
import com.shortener.url_shortener_sb.url.model.UrlMapping;
import com.shortener.url_shortener_sb.url.repository.UrlMappingRepository;
import com.shortener.url_shortener_sb.url.dto.RecordClickEventRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

@Service
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final RestTemplate restTemplate;

    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Value("${analytics.service.url}")
    private String analyticsServiceUrl;

    public UrlMappingService(UrlMappingRepository urlMappingRepository, RestTemplate restTemplate) {
        this.restTemplate=restTemplate;
        this.urlMappingRepository = urlMappingRepository;
    }

    // Creating Url Using username instead of userId
    public UrlMappingDTO createShortUrl(String originalUrl, String username) {

        Long userId = getUserIdFromAuthService(username);

        String shortUrl;
        int attempts =0 ;

        do {
            shortUrl = generateShortUrl();
            attempts++;
            if(attempts>5) {
                throw new IllegalStateException("Could not generate unique short URL");
            }
        } while(urlMappingRepository.existsByShortUrl(shortUrl));

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUserId(userId);
        urlMapping.setCreatedDate(LocalDateTime.now());

        UrlMapping saved = urlMappingRepository.save(urlMapping);

        return convertToDto(saved);
    }

    private Long getUserIdFromAuthService(String username) {
        String url = authServiceUrl + "/api/auth/internal/userid?username=" + username;
        return restTemplate.getForObject(url, Long.class);
    }

    //Generating Short Url
    private String generateShortUrl() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(8);

        for(int i=0; i < 8; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }

        return shortUrl.toString();
    }

    public List<UrlMappingDTO> getUrlsByUsername(String username) {
        Long userId = getUserIdFromAuthService(username);

        return urlMappingRepository
                .findByUserIdOrderByCreatedDateDesc(userId)
                .stream()
                .map(this :: convertToDto)
                .toList();
    }

    // Redirect Logic
    public UrlMapping getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);

        if(urlMapping != null) {
            return null;
        }

        urlMapping.setClickCount(urlMapping.getClickCount()+1);
        urlMappingRepository.save(urlMapping);

        try {
            RecordClickEventRequest request = new RecordClickEventRequest(
                    urlMapping.getId(),
                    urlMapping.getShortUrl()
            );

            restTemplate.postForEntity(
                    analyticsServiceUrl + "/api/analytics/events",
                    request,
                    Void.class
            );
        } catch (Exception ignored) {}

        return urlMapping;
    }

    private UrlMappingDTO convertToDto(UrlMapping urlMapping) {

        UrlMappingDTO dto = new UrlMappingDTO();

        dto.setId(urlMapping.getId());
        dto.setOriginalUrl(urlMapping.getOriginalUrl());
        dto.setShortUrl(urlMapping.getShortUrl());
        dto.setClickCount(urlMapping.getClickCount());
        dto.setCreatedDate(urlMapping.getCreatedDate());
        dto.setUserId(urlMapping.getUserId());

        return dto;
    }

    public Map<LocalDate, Long> getTotalClicksByUsernameAndDate(String username, LocalDate startDate, LocalDate endDate) {
        List<UrlMappingDTO> userUrls = getUrlsByUsername(username);
        Map<LocalDate, Long> aggregated = new TreeMap<>();

        for(UrlMappingDTO url: userUrls) {
            String encodedShortUrl = URLEncoder.encode(url.getShortUrl(), StandardCharsets.UTF_8);
            String urlString = analyticsServiceUrl
                    + "/api/analytics/timeseries/shortUrl/" + encodedShortUrl
                    + "?startDate=" + startDate
                    + "&endDate=" + endDate;

            try {
                ResponseEntity<Map<LocalDate, Long>> response = restTemplate.exchange(
                        urlString,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Map<LocalDate, Long>>() {}
                );

                Map<LocalDate, Long> perUrlMap = response.getBody();
                if (perUrlMap == null) {
                    continue;
                }

                for (Map.Entry<LocalDate, Long> entry : perUrlMap.entrySet()) {
                    aggregated.merge(entry.getKey(), entry.getValue(), Long::sum);
                }
            } catch (Exception ignored) {
            }
        }
        LocalDate cursor = startDate;
        while (!cursor.isAfter(endDate)) {
            aggregated.putIfAbsent(cursor, 0L);
            cursor = cursor.plusDays(1);
        }

        return aggregated;
    }
}