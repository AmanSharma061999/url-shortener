package com.shortener.url_shortener_sb.errors;

import java.time.Instant;

public record ApiError(
    Instant timestamp,
    String path,
    int status,
    String message,
    String traceId
    ) {}