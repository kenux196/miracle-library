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
import java.time.LocalDateTime;
import java.util.List;

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
    @DisplayName("제목 혹은 저자에 대한 키워드로 책을 검색할 수 있어야 한다.")
    void test_BookRepository_findByTitle() {
        final Book book = createBook();
        bookRepository.save(book);

        List<Book> found = bookRepository.findAllByKeyword("title");
        assertThat(found).isNotEmpty();

        found = bookRepository.findAllByKeyword("auth");
        assertThat(found).isNotEmpty();

        found = bookRepository.findAllByKeyword("kkk");
        assertThat(found).isEmpty();
    }

    private Book createBook() {
        return Book.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .createDate(LocalDateTime.now())
                .status(BookStatus.RENTABLE)
                .build();
    }
}
