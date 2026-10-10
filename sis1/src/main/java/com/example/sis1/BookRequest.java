package com.example.sis1;

import jakarta.validation.constraints.*;

public record BookRequest(

        @NotBlank(message = "Title is required")
        @Size(max = 200, message = "Title must be at most 200 characters")
        String title,

        @NotBlank(message = "Author is required")
        @Size(max = 100, message = "Author must be at most 100 characters")
        String author,

        @NotBlank(message = "ISBN is required")
        @Pattern(regexp = "\\d{10}|\\d{13}", message = "ISBN must be 10 or 13 digits")
        String isbn,

        @Min(value = 1450, message = "Published year must be 1450 or later")
        @Max(value = 2100, message = "Published year must be 2100 or earlier")
        Integer publishedYear
) {}