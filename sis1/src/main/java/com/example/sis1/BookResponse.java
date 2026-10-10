package com.example.sis1;

public record BookResponse(
        Long id,
        String title,
        String author,
        String isbn,
        Integer publishedYear
) {
    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPublishedYear()
        );
    }
}