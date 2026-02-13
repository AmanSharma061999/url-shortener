package com.shortener.url_shortener_sb.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public record ShortenRequest (
        @NotBlank(message = "Original Url is required")
        @URL(message = "OriginalUrl is Required!")
        String originalUrl
) {}
