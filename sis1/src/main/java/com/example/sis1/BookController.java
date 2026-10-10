package com.example.sis1;

import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    // Constructor injection
    public BookController(BookService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        BookResponse created = service.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.id())
                .toUri();
        return ResponseEntity.created(location).body(created); // 201 + Location header
    }

    @GetMapping
    public List<BookResponse> findAll() {
        return service.findAll(); // 200
    }

    // ---- Paged queries (Practice 4, part 1) ----

    @GetMapping("/paged")
    public PagedModel<BookResponse> findPage(Pageable pageable) {
        return new PagedModel<>(service.findPage(pageable));
    }

    @GetMapping("/by-title")
    public PagedModel<BookResponse> byTitle(@RequestParam String title, Pageable pageable) {
        return new PagedModel<>(service.findByTitle(title, pageable));
    }

    @GetMapping("/by-author")
    public PagedModel<BookResponse> byAuthor(@RequestParam String author, Pageable pageable) {
        return new PagedModel<>(service.findByAuthor(author, pageable));
    }

    @GetMapping("/by-year")
    public PagedModel<BookResponse> byYear(@RequestParam Integer from, @RequestParam Integer to, Pageable pageable) {
        return new PagedModel<>(service.findByYearRange(from, to, pageable));
    }

    // ---- CRUD by id ----

    @GetMapping("/{id}")
    public BookResponse findById(@PathVariable Long id) {
        return service.findById(id); // 200, or 404 via exception
    }

    @PutMapping("/{id}")
    public BookResponse update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return service.update(id, request); // 200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build(); // 204
    }
}