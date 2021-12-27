package org.kenux.miraclelibrary.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.config.JpaTestConfig;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.enums.BookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaTestConfig.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("책 정보는 저장되어야 한다.")
    void test_BookRepository_hasBookData() {
        final Book book = createBook();
        bookRepository.save(book);

        List<Book> books = bookRepository.findAll();

        assertThat(books).isNotEmpty();
    }

    @Test
    @DisplayName("제목으로 책을 검색할 수 있어야 한다.")
    void test_BookRepository_findByTitle() {
        final Book book = createBook();
        bookRepository.save(book);

        List<Book> found = bookRepository.findAllByTitle("title");
        assertThat(found).isNotEmpty();

        found = bookRepository.findAllByTitle("ti");
        assertThat(found).isNotEmpty();

        found = bookRepository.findAllByTitle("it");
        assertThat(found).isNotEmpty();
    }

    @Test
    @DisplayName("저자를 통해 책을 검색할 수 있다.")
    void test_BookRepository_findByAuthor() {
        // given
        Book book = createBook();
        bookRepository.save(book);

        // when
        List<Book> found = bookRepository.findAllByAuthor("author");

        // then
        assertThat(found).isNotEmpty();
    }

    @Test
    @DisplayName("isbn 을 통해 책을 검색할 수 있다.")
    void test_BookRepository_findByIsbn() {
        // given
        Book book = createBook();
        bookRepository.save(book);

        // when
        List<Book> found = bookRepository.findAllByIsbn("isbn");

        // then
        assertThat(found).isNotEmpty();
    }

    @Test
    @DisplayName("제목과 저자를 통해 책을 검색할 수 있다.")
    void test_BookRepository_searchBookWithTitleAndAuthor() {
        // given
        Book book = createBook();
        bookRepository.save(book);

        // when
        final List<Book> foundBooks = bookRepository.findAllByTitleAndAuthor("ti", "author");

        // then
        assertThat(foundBooks).isNotEmpty();
    }

    private Book createBook() {
        Book book = Book.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .createdDate(LocalDate.now())
                .build();
        book.changeStatus(BookStatus.AVAILABLE);
        return book;
    }
}
