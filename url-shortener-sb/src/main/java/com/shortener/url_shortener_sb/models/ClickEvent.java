package com.shortener.url_shortener_sb.models;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "click_event")
public class ClickEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "click_date" , nullable = false)
    private LocalDateTime clickDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_mapping_id", nullable = false)
    private UrlMapping urlMapping;
}
