package com.shortener.url_shortener_sb.url.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(
        name = "url_mapping",
        indexes = {
                @Index(name = "idx_url_mapping_short_url", columnList = "short_url"),
                @Index(name = "idx_url_mapping_user_id", columnList = "user_id"),
                @Index(name = "idx_url_mapping_created_date", columnList = "created_date")
        }
)
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

    @Column(name = "short_url", nullable = false, unique = true)
    private String shortUrl;

    @Column(name = "click_count", nullable = false)
    private int clickCount = 0;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @PrePersist
    void onCreate() {
        this.createdDate = LocalDateTime.now();
    }
}





/*
    * Cascade Style means propagating operations from parent to child
    * Orphan removal stands to delete child when it losses its parent.

    * Hey JPA, just before you save this object for the first time, run this method. -@PrePersist
    * You use @PrePersist to initialize or modify fields just before INSERT happens,
       without doing it manually in your service code.



 */