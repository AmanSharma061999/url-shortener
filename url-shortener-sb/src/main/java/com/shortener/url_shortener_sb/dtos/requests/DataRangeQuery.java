package com.shortener.url_shortener_sb.dtos.requests;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record DataRangeQuery(
        @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
        LocalDate startDate,

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate endDate
)
{}
