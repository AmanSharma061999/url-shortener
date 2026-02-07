package com.shortener.url_shortener_sb.dtos;

import lombok.Data;

@Data
public class LoginRequest {

    private String username;
    private String password;
}
