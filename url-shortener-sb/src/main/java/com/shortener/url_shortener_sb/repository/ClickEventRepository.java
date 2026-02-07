package com.shortener.url_shortener_sb.repository;

import com.shortener.url_shortener_sb.models.ClickEvent;
import com.shortener.url_shortener_sb.models.UrlMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClickEventRepository extends JpaRepository<ClickEvent, Long> {
    List<ClickEvent> findByUrlMappingAndClickDateBetween(UrlMapping mapping, LocalDateTime startDate,LocalDateTime endDate);
    List<ClickEvent> findByUrlMappingInAndClickDateBetween(List<UrlMapping> mapping, LocalDateTime startDate,LocalDateTime endDate);
    //This will get us totalClicks by a particular user between start and end Date, it recognises it is of particular user
    //cause we send the List of UrlMapping of that particular user.
}
