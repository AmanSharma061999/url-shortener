package com.shortener.url_shortener_sb.analytics.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(
        name = "click_event",
        indexes = {
                @Index(name = "idx_click_event_link_id_click_date", columnList = "link_id, click_date"),
                @Index(name = "idx_click_event_short_url_click_date", columnList = "short_url, click_date"),
                @Index(name = "idx_click_event_click_date", columnList = "click_date")
        }
)
public class ClickEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "link_id", nullable = false)
    private Long linkId;

    @Column(name = "short_url", nullable = false, length = 255)
    private String shortUrl;

    @Column(name = "click_date", nullable = false)
    private LocalDateTime clickDate;

    @PrePersist
    void onCreate() {
        if (this.clickDate == null) {
            this.clickDate = LocalDateTime.now();
        }
    }
}