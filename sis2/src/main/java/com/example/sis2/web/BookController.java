package com.example.sis2.web;

import com.example.sis2.dto.BookRequest;
import com.example.sis2.dto.BookResponse;
import com.example.sis2.mapper.BookMapper;
import com.example.sis2.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService service;
    private final BookMapper mapper;

    public BookController(BookService service, BookMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookResponse create(@Valid @RequestBody BookRequest request) {
        return mapper.toResponse(service.create(mapper.toDomain(request)));
    }

    @GetMapping
    public List<BookResponse> getAll() {
        return service.getAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public BookResponse getById(@PathVariable Long id) {
        return mapper.toResponse(service.getById(id));
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return mapper.toResponse(service.update(id, mapper.toDomain(request)));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}