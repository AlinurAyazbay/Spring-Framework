package com.example.sis2.service;

import com.example.sis2.domain.Book;
import com.example.sis2.mapper.BookMapper;
import com.example.sis2.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;
    private final BookMapper mapper;

    public BookService(BookRepository repository, BookMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public Book create(Book book) {
        return mapper.toDomain(repository.save(mapper.toEntity(book)));
    }

    public List<Book> getAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    public Book getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new BookNotFoundException(id));
    }

    public Book update(Long id, Book book) {
        if (!repository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        Book withId = new Book(id, book.title(), book.author(), book.publicationYear(), book.price());
        return mapper.toDomain(repository.save(mapper.toEntity(withId)));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        repository.deleteById(id);
    }
}