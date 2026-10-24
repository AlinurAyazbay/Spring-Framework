package com.example.sis2.web;

import com.example.sis2.domain.Book;
import com.example.sis2.mapper.BookMapper;
import com.example.sis2.service.BookNotFoundException;
import com.example.sis2.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(BookMapper.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService service;

    @Test
    void create_withValidBody_returns201() throws Exception {
        String json = """
                {"title":"Clean Code","author":"Martin","publicationYear":2008,"price":30.00}
                """;
        when(service.create(any(Book.class)))
                .thenReturn(new Book(1L, "Clean Code", "Martin", 2008, new BigDecimal("30.00")));

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    void create_withBlankTitle_returns400() throws Exception {
        String json = """
                {"title":"","author":"Martin","publicationYear":2008,"price":30.00}
                """;

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").exists());
    }

    @Test
    void getAll_returnsList() throws Exception {
        when(service.getAll()).thenReturn(List.of(
                new Book(1L, "A", "X", 2000, new BigDecimal("5.00")),
                new Book(2L, "B", "Y", 2001, new BigDecimal("6.00"))));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("A"));
    }

    @Test
    void getById_whenMissing_returns404() throws Exception {
        when(service.getById(99L)).thenThrow(new BookNotFoundException(99L));

        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Book with id 99 not found"));
    }

    @Test
    void delete_returns204() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }
}