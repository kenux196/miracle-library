package org.kenux.miraclelibrary.domain.book.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.kenux.miraclelibrary.global.config.QueryDslConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QueryDslConfig.class)
//@ActiveProfiles("dev")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    void afterEach() {
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("보유 도서 저장")
    void save_success() {
        // given
        final Book book = Book.createNewBook();

        // when
        final Book save = bookRepository.save(book);

        // then
        Optional<Book> findBook = bookRepository.findById(save.getId());
        assertThat(findBook).isPresent();
        assertThat(findBook.get().getId()).isEqualTo(save.getId());
    }

    @Test
    @DisplayName("도서 상태 검색: 분실책")
    void findAllByStatus_Lost_Test() throws Exception {
        // given
        final List<Book> bookList = createBookList();
        final long count = bookList.stream()
                .filter(book -> book.getStatus().equals(BookStatus.LOST))
                .count();

        // when
        List<Book> result = bookRepository.findAllByStatus(BookStatus.LOST);

        // then
        assertThat(result).hasSize((int) count);
    }

    @Test
    @DisplayName("도서상태 검색: 파기상태")
    void findAllByStatus_Removed_Test() throws Exception {
        // given
        final List<Book> bookList = createBookList();
        final long count = bookList.stream()
                .filter(book -> book.getStatus().equals(BookStatus.REMOVED))
                .count();

        // when
        List<Book> result = bookRepository.findAllByStatus(BookStatus.REMOVED);

        // then
        assertThat(result).hasSize((int) count);
    }

    @Test
    @DisplayName("도서상태 겁색: 대여상태")
    void findAllByStatus_Rented_Test() throws Exception {
        // given
        final List<Book> bookList = createBookList();
        final long count = bookList.stream()
                .filter(book -> book.getStatus().equals(BookStatus.RENTED))
                .count();

        // when
        List<Book> result = bookRepository.findAllByStatus(BookStatus.RENTED);

        // then
        assertThat(result).hasSize((int) count);
    }

    @Test
    @DisplayName("도서상태 겁색: 대여가능상태")
    void findAllByStatus_Rentable_Test() throws Exception {
        // given
        final List<Book> bookList = createBookList();
        final long count = bookList.stream()
                .filter(book -> book.getStatus().equals(BookStatus.RENTABLE))
                .count();

        // when
        List<Book> result = bookRepository.findAllByStatus(BookStatus.RENTABLE);

        // then
        assertThat(result).hasSize((int) count);
    }

    private List<Book> createBookList() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final Book book = Book.createNewBook();
            if (i == 0) {
                book.changeBookStatus(BookStatus.REMOVED);
            } else if (i == 1) {
                book.changeBookStatus(BookStatus.LOST);
            } else if (i < 5) {
                book.changeBookStatus(BookStatus.RENTED);
            } else {
                book.changeBookStatus(BookStatus.RENTABLE);
            }
            bookRepository.save(book);
            books.add(book);
        }
        return books;
    }
}
