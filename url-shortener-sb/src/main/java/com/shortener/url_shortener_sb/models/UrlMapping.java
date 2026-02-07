package com.shortener.url_shortener_sb.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "url_mapping")
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

    @Column(name = "short_url",nullable = false,unique = true)
    private String shortUrl;

    @Column(name = "click_count", nullable = false)
    private int clickCount = 0;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @PrePersist
    void onCreate() {
        this.createdDate =  LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)                      //Lazy only fetch the user when accessed
    @JoinColumn(name = "user_id", nullable = false)         //This specify the foreign key linking of this table to user table;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @OneToMany(mappedBy = "urlMapping", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<ClickEvent> clickEvents = new ArrayList<>();

}





//Cascade Style means propagating operations from parent to child
// Orphan removal stands to delete child when it losses its parent.