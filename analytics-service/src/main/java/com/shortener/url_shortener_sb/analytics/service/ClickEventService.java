package com.shortener.url_shortener_sb.analytics.service;

import com.shortener.url_shortener_sb.analytics.dto.ClickEventDTO;
import com.shortener.url_shortener_sb.analytics.dto.RecordClickEventRequest;
import com.shortener.url_shortener_sb.analytics.model.ClickEvent;
import com.shortener.url_shortener_sb.analytics.repository.ClickEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClickEventService {

    private final ClickEventRepository clickEventRepository;

    public ClickEventService(ClickEventRepository clickEventRepository) {
        this.clickEventRepository = clickEventRepository;
    }

    public void recordClick(RecordClickEventRequest request) {
        ClickEvent clickEvent = new ClickEvent();
        clickEvent.setLinkId(request.linkId());
        clickEvent.setShortUrl(request.shortUrl());
        clickEvent.setClickDate(LocalDateTime.now());
        clickEventRepository.save(clickEvent);
    }

    public long getCountByLinkId(Long linkId) {
        return clickEventRepository.countByLinkId(linkId);
    }

    public long getCountByShortUrl(String shortUrl) {
        return clickEventRepository.countByShortUrl(shortUrl);
    }

    public List<ClickEventDTO> getClicksByLinkId(Long linkId, LocalDateTime start, LocalDateTime end) {
        return clickEventRepository.findByLinkIdAndClickDateBetween(linkId, start, end)
                .stream()
                .collect(Collectors.groupingBy(
                        click -> click.getClickDate().toLocalDate(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> {
                    ClickEventDTO dto = new ClickEventDTO();
                    dto.setClickDate(entry.getKey());
                    dto.setCount(entry.getValue());
                    return dto;
                })
                .toList();
    }

    public List<ClickEventDTO> getClicksByShortUrl(String shortUrl, LocalDateTime start, LocalDateTime end) {
        return clickEventRepository.findByShortUrlAndClickDateBetween(shortUrl, start, end)
                .stream()
                .collect(Collectors.groupingBy(
                        click -> click.getClickDate().toLocalDate(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(entry -> {
                    ClickEventDTO dto = new ClickEventDTO();
                    dto.setClickDate(entry.getKey());
                    dto.setCount(entry.getValue());
                    return dto;
                })
                .toList();
    }
}