package com.example.sis2.dto;

import java.math.BigDecimal;

public record BookResponse(Long id, String title, String author, Integer publicationYear, BigDecimal price) {
}