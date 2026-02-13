package com.shortener.url_shortener_sb.dtos.responses;

import java.util.List;

public record MeResponse (
        boolean ok,
        String username,
        List<String> roles
){}
