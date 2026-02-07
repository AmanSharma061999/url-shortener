package com.shortener.url_shortener_sb.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClickEventDTO {
    private LocalDate clickDate;
    private Long count;
}
