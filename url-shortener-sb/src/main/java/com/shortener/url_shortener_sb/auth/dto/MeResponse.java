package com.shortener.url_shortener_sb.auth.dto;

import java.util.List;

public record MeResponse (
        boolean ok,
        String username,
        List<String> roles
){}
