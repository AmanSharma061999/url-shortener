package com.shortener.url_shortener_sb.analytics.repository;

import com.shortener.url_shortener_sb.analytics.model.ClickEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {

    List<ClickEvent> findByLinkIdAndClickDateBetween(Long linkId, LocalDateTime startDate, LocalDateTime endDate);

    List<ClickEvent> findByShortUrlAndClickDateBetween(String shortUrl, LocalDateTime startDate, LocalDateTime endDate);

    long countByLinkId(Long linkId);

    long countByShortUrl(String shortUrl);
}