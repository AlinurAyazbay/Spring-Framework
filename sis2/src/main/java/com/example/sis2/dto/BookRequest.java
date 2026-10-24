package com.example.sis2.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record BookRequest(
        @NotBlank(message = "title must not be blank") String title,
        @NotBlank(message = "author must not be blank") String author,
        @NotNull @Min(value = 1000, message = "publicationYear must be >= 1000") Integer publicationYear,
        @NotNull @Positive(message = "price must be positive") BigDecimal price) {
}