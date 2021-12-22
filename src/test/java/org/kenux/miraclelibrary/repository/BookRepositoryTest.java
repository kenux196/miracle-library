package org.kenux.miraclelibrary.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.config.JpaTestConfig;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.enums.BookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

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
        final Book book = new Book("title", "author", "isbn", 3);
        book.changeStatus(BookStatus.AVAILABLE_FOR_RENTAL);
        bookRepository.save(book);

        List<Book> books = bookRepository.findAll();

        assertThat(books).isNotEmpty();
    }

    @Test
    @DisplayName("제목으로 책을 검색할 수 있어야 한다.")
    void test_BookRepository_findByTitle() {
        final Book book = new Book("title", "author", "isbn", 3);
        book.changeStatus(BookStatus.AVAILABLE_FOR_RENTAL);
        bookRepository.save(book);

        Optional<Book> found = bookRepository.findByTitle("title");

        assertThat(found).isNotEmpty();
        assertThat(found.get().getTitle()).isEqualTo("title");
        assertThat(found.get().getAuthor()).isEqualTo("author");
        assertThat(found.get().getIsbn()).isEqualTo("isbn");
        assertThat(found.get().getAmount()).isEqualTo(3);
    }

    // TODO : 저자, isbn을 통한 검색 테스트   - sky 2021/12/23

    // TODO : 제목의 일부만으로 검색 테스트   - sky 2021/12/23
}