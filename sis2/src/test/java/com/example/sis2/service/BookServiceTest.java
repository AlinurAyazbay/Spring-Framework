package com.example.sis2.service;

import com.example.sis2.domain.Book;
import com.example.sis2.entity.BookEntity;
import com.example.sis2.mapper.BookMapper;
import com.example.sis2.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository repository;

    private BookService service;

    @BeforeEach
    void setUp() {
        service = new BookService(repository, new BookMapper());
    }

    private BookEntity entity(Long id, String title) {
        BookEntity e = new BookEntity();
        e.setId(id);
        e.setTitle(title);
        e.setAuthor("Author");
        e.setPublicationYear(2000);
        e.setPrice(new BigDecimal("10.00"));
        return e;
    }

    @Test
    void create_savesAndReturnsBookWithGeneratedId() {
        when(repository.save(any(BookEntity.class))).thenAnswer(inv -> {
            BookEntity saved = inv.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        Book result = service.create(new Book(null, "Clean Code", "Martin", 2008, new BigDecimal("30.00")));

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.title()).isEqualTo("Clean Code");
    }

    @Test
    void getAll_returnsMappedBooks() {
        when(repository.findAll()).thenReturn(List.of(entity(1L, "A"), entity(2L, "B")));

        List<Book> result = service.getAll();

        assertThat(result).hasSize(2);
        assertThat(result.get(1).title()).isEqualTo("B");
    }

    @Test
    void getById_whenExists_returnsBook() {
        when(repository.findById(1L)).thenReturn(Optional.of(entity(1L, "A")));

        assertThat(service.getById(1L).title()).isEqualTo("A");
    }

    @Test
    void getById_whenMissing_throwsNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById(99L))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void delete_whenMissing_throwsAndDoesNotDelete() {
        when(repository.existsById(5L)).thenReturn(false);

        assertThatThrownBy(() -> service.delete(5L)).isInstanceOf(BookNotFoundException.class);
        verify(repository, never()).deleteById(any());
    }

    @Test
    void delete_whenExists_deletes() {
        when(repository.existsById(5L)).thenReturn(true);

        service.delete(5L);

        verify(repository).deleteById(5L);
    }
}