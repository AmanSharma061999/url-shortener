package com.shortener.url_shortener_sb.url.repository;

import com.shortener.url_shortener_sb.url.model.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlMappingRepository extends JpaRepository<UrlMapping, Long> {
    UrlMapping findByShortUrl(String shortUrl);
    List<UrlMapping> findByUserIdOrderByCreatedDateDesc(Long userId);
    boolean existsByShortUrl(String shortUrl);
}