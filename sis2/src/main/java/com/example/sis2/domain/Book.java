package com.example.sis2.domain;

import java.math.BigDecimal;

public record Book(Long id, String title, String author, Integer publicationYear, BigDecimal price) {
}