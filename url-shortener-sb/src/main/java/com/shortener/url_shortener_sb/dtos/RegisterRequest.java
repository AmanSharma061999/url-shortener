package com.shortener.url_shortener_sb.dtos;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String role;
    private String password;

}
