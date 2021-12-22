package org.kenux.miraclelibrary.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookMemoryRepositoryTest {

    private final BookMemoryRepository bookRepository = BookMemoryRepository.getInstance();

    @BeforeEach
    void init() {
        bookRepository.clear();
    }

    @Test
    @DisplayName("새로운 책은 책 저장소에 정보가 저장된다.")
    void test_BookRepository_hasBookData() {
        final Book book = new Book("title", "author", "isbn", 3);
        bookRepository.save(book);

        List<Book> books = bookRepository.findAll();

        assertThat(books).isNotEmpty();
    }

    @Test
    @DisplayName("제목으로 책을 검색할 수 있어야 한다.")
    void test_BookRepository_findByTitle() {
        final Book book = new Book("title", "author", "isbn", 3);
        bookRepository.save(book);

        List<Book> books = bookRepository.findAllByTitle("title");

        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getTitle()).isEqualTo("title");
    }
}