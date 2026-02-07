package com.shortener.url_shortener_sb.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "users",
       uniqueConstraints = {
        @UniqueConstraint(name = "uk_users_email", columnNames = "email"),
               @UniqueConstraint(name = "uk_users_username", columnNames = "username")
       })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role = "ROLE_USER";
}

