package com.shortener.url_shortener_sb.analytics.service;

import com.shortener.url_shortener_sb.analytics.dto.ClickEventDTO;
import com.shortener.url_shortener_sb.analytics.dto.RecordClickEventRequest;
import com.shortener.url_shortener_sb.analytics.model.ClickEvent;
import com.shortener.url_shortener_sb.analytics.repository.ClickEventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
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


    public List<ClickEventDTO> getClicksByLinkId(Long linkId, LocalDateTime startDate, LocalDateTime endDate) {
        List<ClickEvent> events =
                clickEventRepository.findByLinkIdAndClickDateBetweenOrderByClickDateDesc(linkId, startDate, endDate);

        return toDailyCounts(events, startDate.toLocalDate(), endDate.toLocalDate());
    }

    public List<ClickEventDTO> getClicksByShortUrl(String shortUrl, LocalDateTime startDate, LocalDateTime endDate) {
        List<ClickEvent> events =
                clickEventRepository.findByShortUrlAndClickDateBetweenOrderByClickDateDesc(shortUrl, startDate, endDate);

        return toDailyCounts(events, startDate.toLocalDate(), endDate.toLocalDate());
    }

    public Map<LocalDate, Long> getClickCountTimeSeriesByShortUrl(String shortUrl, LocalDate startDate, LocalDate endDate) {
        LocalDateTime from = startDate.atStartOfDay();
        LocalDateTime to = endDate.atTime(LocalTime.MAX);

        List<ClickEvent> events =
                clickEventRepository.findByShortUrlAndClickDateBetweenOrderByClickDateDesc(shortUrl, from, to);

        Map<LocalDate, Long> result = new TreeMap<>();

        LocalDate cursor = startDate;
        while (!cursor.isAfter(endDate)) {
            result.put(cursor, 0L);
            cursor = cursor.plusDays(1);
        }

        for (ClickEvent event : events) {
            LocalDate day = event.getClickDate().toLocalDate();
            result.merge(day, 1L, Long::sum);
        }

        return result;
    }

    private List<ClickEventDTO> toDailyCounts(List<ClickEvent> events, LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, Long> counts = new TreeMap<>();

        LocalDate cursor = startDate;
        while (!cursor.isAfter(endDate)) {
            counts.put(cursor, 0L);
            cursor = cursor.plusDays(1);
        }

        for (ClickEvent event : events) {
            LocalDate day = event.getClickDate().toLocalDate();
            counts.merge(day, 1L, Long::sum);
        }

        List<ClickEventDTO> result = new ArrayList<>();
        for (Map.Entry<LocalDate, Long> entry : counts.entrySet()) {
            ClickEventDTO dto = new ClickEventDTO();
            dto.setClickDate(entry.getKey());
            dto.setCount(entry.getValue());
            result.add(dto);
        }

        return result;
    }

    /*
    public List<ClickEventDTO> getClicksByLinkId(Long linkId, LocalDateTime start, LocalDateTime end) {
        return clickEventRepository.findByLinkIdAndClickedAtBetweenOrderByClickedAtDesc(linkId, startDate, endDate)
                .stream()
                .map(this::toDto)
                .toList();
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
        return clickEventRepository.findByShortUrlAndClickedAtBetweenOrderByClickedAtDesc(shortUrl, startDate, endDate)
                .stream()
                .map(this::toDto)
                .toList();
        /*return clickEventRepository.findByShortUrlAndClickDateBetween(shortUrl, start, end)
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


    public Map<LocalDate, Long> getClickCountTimeSeriesByShortUrl(String shortUrl, LocalDate startDate, LocalDate endDate) {
        LocalDateTime from = startDate.atStartOfDay();
        LocalDateTime to = endDate.atTime(LocalTime.MAX);

        List<ClickEvent> events =
                clickEventRepository.findByShortUrlAndClickedAtBetweenOrderByClickedAtDesc(shortUrl, from, to);

        Map<LocalDate, Long> result = new TreeMap<>();

        LocalDate cursor = startDate;
        while (!cursor.isAfter(endDate)) {
            result.put(cursor, 0L);
            cursor = cursor.plusDays(1);
        }

        for (ClickEvent event : events) {
            LocalDate day = event.getClickedAt().toLocalDate();
            result.merge(day, 1L, Long::sum);
        }

        return result;
    }

    private ClickEventDTO toDto(ClickEvent event) {
        ClickEventDTO dto = new ClickEventDTO();
        dto.setId(event.getId());
        dto.setLinkId(event.getLinkId());
        dto.setShortUrl(event.getShortUrl());
        dto.setClickedAt(event.getClickedAt());
        return dto;
    }*/
}