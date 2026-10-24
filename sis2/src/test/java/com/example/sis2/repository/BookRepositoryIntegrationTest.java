package com.example.sis2.repository;

import com.example.sis2.entity.BookEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Testcontainers
class BookRepositoryIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:16-alpine");

    @Autowired
    private BookRepository repository;

    private BookEntity book(String title, String author) {
        BookEntity e = new BookEntity();
        e.setTitle(title);
        e.setAuthor(author);
        e.setPublicationYear(2000);
        e.setPrice(new BigDecimal("12.50"));
        return e;
    }

    @Test
    void save_assignsIdAndCanBeFound() {
        BookEntity saved = repository.save(book("Dune", "Herbert"));

        assertThat(saved.getId()).isNotNull();
        assertThat(repository.findById(saved.getId()))
                .isPresent()
                .get().extracting(BookEntity::getTitle).isEqualTo("Dune");
    }

    @Test
    void findByAuthorIgnoreCase_returnsOnlyMatchingBooks() {
        repository.save(book("Dune", "Herbert"));
        repository.save(book("Emma", "Austen"));

        List<BookEntity> result = repository.findByAuthorIgnoreCase("HERBERT");

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getTitle()).isEqualTo("Dune");
    }
}