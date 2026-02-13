package com.shortener.url_shortener_sb.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "click_event",
    indexes = {
            @Index(name="idx_click_event_mapping_date", columnList = "url_mapping_id, click_date"),
            @Index(name ="idx_click_event_click_date", columnList = "click_date")
    })
public class ClickEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "click_date" , nullable = false)
    private LocalDateTime clickDate;

    @PrePersist
    void onCreate() {
        if(this.clickDate == null) this.clickDate=LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_mapping_id", nullable = false)
    private UrlMapping urlMapping;
}
