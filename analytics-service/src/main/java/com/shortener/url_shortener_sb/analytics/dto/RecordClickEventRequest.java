package com.shortener.url_shortener_sb.analytics.dto;

public record RecordClickEventRequest(
        Long linkId,
        String shortUrl
) {}