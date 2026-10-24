package com.example.sis2.repository;

import com.example.sis2.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByAuthorIgnoreCase(String author);
}