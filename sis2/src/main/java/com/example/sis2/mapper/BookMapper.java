package com.example.sis2.mapper;

import com.example.sis2.domain.Book;
import com.example.sis2.dto.BookRequest;
import com.example.sis2.dto.BookResponse;
import com.example.sis2.entity.BookEntity;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    // DTO -> domain
    public Book toDomain(BookRequest request) {
        return new Book(null, request.title(), request.author(),
                request.publicationYear(), request.price());
    }

    // entity -> domain
    public Book toDomain(BookEntity entity) {
        return new Book(entity.getId(), entity.getTitle(), entity.getAuthor(),
                entity.getPublicationYear(), entity.getPrice());
    }

    // domain -> entity
    public BookEntity toEntity(Book book) {
        BookEntity entity = new BookEntity();
        entity.setId(book.id());
        entity.setTitle(book.title());
        entity.setAuthor(book.author());
        entity.setPublicationYear(book.publicationYear());
        entity.setPrice(book.price());
        return entity;
    }

    // domain -> DTO
    public BookResponse toResponse(Book book) {
        return new BookResponse(book.id(), book.title(), book.author(),
                book.publicationYear(), book.price());
    }
}