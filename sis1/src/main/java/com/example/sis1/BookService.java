package com.example.sis1;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository repository;

    // Constructor injection: Spring passes in the repository bean
    public BookService(BookRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public BookResponse create(BookRequest request) {
        if (repository.existsByIsbn(request.isbn())) {
            throw new DuplicateIsbnException(request.isbn());
        }
        Book book = new Book();
        apply(book, request);
        return BookResponse.from(repository.save(book));
    }

    @Transactional(readOnly = true)
    public List<BookResponse> findAll() {
        return repository.findAll().stream().map(BookResponse::from).toList();
    }

    @Transactional(readOnly = true)
    public Page<BookResponse> findPage(Pageable pageable) {
        return repository.findAll(pageable).map(BookResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<BookResponse> findByTitle(String title, Pageable pageable) {
        return repository.findByTitleContainingIgnoreCase(title, pageable).map(BookResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<BookResponse> findByAuthor(String author, Pageable pageable) {
        return repository.findByAuthorContainingIgnoreCase(author, pageable).map(BookResponse::from);
    }

    @Transactional(readOnly = true)
    public Page<BookResponse> findByYearRange(Integer from, Integer to, Pageable pageable) {
        return repository.findByPublishedYearBetween(from, to, pageable).map(BookResponse::from);
    }

    @Transactional(readOnly = true)
    public BookResponse findById(Long id) {
        return BookResponse.from(getOrThrow(id));
    }

    @Transactional
    public BookResponse update(Long id, BookRequest request) {
        Book book = getOrThrow(id);
        boolean isbnChanged = !book.getIsbn().equals(request.isbn());
        if (isbnChanged && repository.existsByIsbn(request.isbn())) {
            throw new DuplicateIsbnException(request.isbn());
        }
        apply(book, request);
        return BookResponse.from(repository.save(book));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new BookNotFoundException(id);
        }
        repository.deleteById(id);
    }

    private Book getOrThrow(Long id) {
        return repository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    private void apply(Book book, BookRequest request) {
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setIsbn(request.isbn());
        book.setPublishedYear(request.publishedYear());
    }
}