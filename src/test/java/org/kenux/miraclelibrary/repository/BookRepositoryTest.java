package org.kenux.miraclelibrary.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.Book;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookRepositoryTest {

    private final BookRepository bookRepository = BookRepository.getInstance();

    @BeforeEach
    void init() {
        bookRepository.clear();
    }

    @Test
    @DisplayName("책 저장소는 유일해야 한다.")
    void test_BookRepository_isUnique() {
        final BookRepository bookRepository = BookRepository.getInstance();
        final BookRepository bookRepository2 = BookRepository.getInstance();

        assertThat(bookRepository).isEqualTo(bookRepository2);
    }

    @Test
    @DisplayName("새로운 책은 책 저장소에 정보가 저장된다.")
    void test_BookRepository_hasBookData() {
        final Book book = new Book("title", "author", "isbn");
        bookRepository.save(book);

        List<Book> books = bookRepository.findAll();

        assertThat(books).isNotEmpty();
    }

    @Test
    @DisplayName("제목으로 책을 검색할 수 있어야 한다.")
    void test_BookRepository_findByTitle() {
        final Book book = new Book("title", "author", "isbn");
        bookRepository.save(book);

        List<Book> books = bookRepository.findAllByTitle("title");

        assertThat(books).isNotEmpty();
        assertThat(books.get(0).getTitle()).isEqualTo("title");
    }

    @Test
    @DisplayName("동일한 책은 여러권 등록이 가능해야 한다.")
    void test_SameBook_availableRegister() {
        final Book book = new Book("title", "author", "isbn");
        bookRepository.save(book);
        final Book book2 = new Book("title", "author", "isbn");
        bookRepository.save(book2);

        final List<Book> books = bookRepository.findAllByTitle("title");

        assertThat(books).hasSize(2);
    }

    @Test
    void test_보관된_책은_고유한_아이디를_가져야_한다() {
        final Book book = new Book("title", "author", "isbn");
        Book savedBook = bookRepository.save(book);

        assertThat(savedBook.getId()).isEqualTo(1);
    }
}