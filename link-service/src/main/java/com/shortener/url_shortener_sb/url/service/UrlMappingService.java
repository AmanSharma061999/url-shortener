package com.shortener.url_shortener_sb.url.service;

import com.shortener.url_shortener_sb.url.dto.UrlMappingDTO;
import com.shortener.url_shortener_sb.url.model.UrlMapping;
import com.shortener.url_shortener_sb.url.repository.UrlMappingRepository;
import com.shortener.url_shortener_sb.url.dto.RecordClickEventRequest;
import org.springframework.web.client.RestTemplate;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class UrlMappingService {

    private final UrlMappingRepository urlMappingRepository;
    private final RestTemplate restTemplate;

    public UrlMappingService(UrlMappingRepository urlMappingRepository, RestTemplate restTemplate) {
        this.restTemplate=restTemplate;
        this.urlMappingRepository = urlMappingRepository;
    }

    public UrlMappingDTO createShortUrl(String originalUrl, Long userId) {
        String shortUrl;
        int attempts = 0;

        do {
            shortUrl = generateShortUrl();
            attempts++;
            if (attempts > 5) {
                throw new IllegalStateException("Could not generate unique short URL");
            }
        } while (urlMappingRepository.existsByShortUrl(shortUrl));

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setOriginalUrl(originalUrl);
        urlMapping.setShortUrl(shortUrl);
        urlMapping.setUserId(userId);
        urlMapping.setCreatedDate(LocalDateTime.now());

        UrlMapping saved = urlMappingRepository.save(urlMapping);
        return convertToDto(saved);
    }

    public List<UrlMappingDTO> getUrlsByUserId(Long userId) {
        return urlMappingRepository.findByUserIdOrderByCreatedDateDesc(userId)
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    public UrlMapping getOriginalUrl(String shortUrl) {
        UrlMapping urlMapping = urlMappingRepository.findByShortUrl(shortUrl);
        if (urlMapping != null) {
            urlMapping.setClickCount(urlMapping.getClickCount() + 1);
            urlMappingRepository.save(urlMapping);
        }

        try {

            RecordClickEventRequest request =
                    new RecordClickEventRequest(
                            urlMapping.getId(),
                            urlMapping.getShortUrl()
                    );

            restTemplate.postForEntity(
                    "http://localhost:8083/api/analytics/events",
                    request,
                    Void.class
            );

        } catch (Exception ignored) {
        }

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

    private String generateShortUrl() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder shortUrl = new StringBuilder(8);

        for (int i = 0; i < 8; i++) {
            shortUrl.append(characters.charAt(random.nextInt(characters.length())));
        }
        return shortUrl.toString();
    }
}