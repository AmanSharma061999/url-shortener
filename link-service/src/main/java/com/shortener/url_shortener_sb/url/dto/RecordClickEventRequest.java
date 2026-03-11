package com.shortener.url_shortener_sb.url.dto;

public record RecordClickEventRequest(
        Long linkId,
        String shortUrl
) {}