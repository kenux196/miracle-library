package org.kenux.miraclelibrary.domain.book.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.kenux.miraclelibrary.global.config.QueryDslConfig;
import org.kenux.miraclelibrary.web.book.dto.request.BookSearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(QueryDslConfig.class)
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void beforeEach() {
        bookRepository.deleteAll();
    }

    @Test
    @DisplayName("저장: 정상 케이스")
    void save_정상() {
        final Book book = createBook();

        final Book save = bookRepository.save(book);

        assertThat(save.getId()).isNotNull();
        assertThat(save).isSameAs(book);
        assertThat(save.getId()).isEqualTo(book.getId());
    }

    @Test
    @DisplayName("저장: 필수항목 없으면, 예외 발생")
    void save_필수항목없음_예외발생() {
        // given
        final Book book = Book.builder().build();

        // when then
        assertThatThrownBy(() -> bookRepository.save(book))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("필터 검색기능: 필터에 키워드만 있는 경우")
    void findBookByFilter_키워드값만있는경우() throws Exception {
        // given
        final Book book = createBook();
        final Book saved = bookRepository.save(book);
        BookSearchFilter bookSearchFilter = BookSearchFilter.builder()
                .keyword("title")
                .build();

        // when
        List<Book> result = bookRepository.findBookByFilter(bookSearchFilter);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("필터 검색기능: 필터에 카테고리값만 있는 경우")
    void findBookByFilter_카테고리값만있는경우() throws Exception {
        // given
        final Book book = createBook();
        final Book saved = bookRepository.save(book);
        BookSearchFilter bookSearchFilter = BookSearchFilter.builder()
                .category(BookCategory.ESSAY)
                .build();

        // when
        List<Book> result = bookRepository.findBookByFilter(bookSearchFilter);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isSameAs(saved);
    }

    @Test
    @DisplayName("필터 검색기능: 키워드, 카테고리 모두 있는 경우, 카테고리 And Keyword 겁색한다.")
    void findBookByFilter_키워드카테고리모두있는경우() throws Exception {
        // given
        final Book book = createBook();
        final Book saved = bookRepository.save(book);
        BookSearchFilter bookSearchFilter = BookSearchFilter.builder()
                .keyword("ti")
                .category(BookCategory.ESSAY)
                .build();

        // when
        List<Book> result = bookRepository.findBookByFilter(bookSearchFilter);

        // then
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isSameAs(saved);
    }

    @Test
    @DisplayName("필터 검색기능: 검색결과 없는 경우")
    void findBookByFilter_검색결과없는경우() throws Exception {
        // given
        final Book book = createBook();
        bookRepository.save(book);
        BookSearchFilter bookSearchFilter = BookSearchFilter.builder()
                .keyword("ti")
                .category(BookCategory.IT)
                .build();

        // when
        List<Book> result = bookRepository.findBookByFilter(bookSearchFilter);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("전체 항목 수")
    void countAllBooks() throws Exception {
        // given
        final List<Book> bookList = createBookList();

        // when
        final long count = bookRepository.count();

        // then
        assertThat(count).isEqualTo(bookList.size());
    }

    @Test
    @DisplayName("도서상태 검색: 분실책")
    void findAllByStatus_분실상태() throws Exception {
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
    void findAllByStatus_파기상태() throws Exception {
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
    void findAllByStatus_대여상태() throws Exception {
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
    void findAllByStatus_대여가능상태() throws Exception {
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

    @Test
    @DisplayName("도서 검색: 한달 이내에 출간된 신간도서 조회")
    void findNewBookWithinOneMonth() throws Exception {
        // given
        final Book book1 = Book.builder()
                .title("book1")
                .author("author1")
                .isbn("123")
                .status(BookStatus.RENTABLE)
                .publishDate(LocalDate.of(2020, 1, 1))
                .build();
        bookRepository.save(book1);
        final Book book2 = Book.builder()
                .title("book2")
                .author("author2")
                .isbn("123")
                .status(BookStatus.RENTABLE)
                .publishDate(LocalDate.of(2022, 1, 1))
                .build();
        bookRepository.save(book2);

        LocalDate today = LocalDate.of(2022, 1, 11);

        // when
        final List<Book> newBookWithinOneMonth =
                bookRepository.findNewBookWithinOneMonth(today);

        // then
        assertThat(newBookWithinOneMonth).hasSize(1);
        assertThat(newBookWithinOneMonth.get(0).getTitle()).isEqualTo("book2");
    }

    private List<Book> createBookList() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final Book book = Book.builder()
                    .title("book" + i)
                    .author("author" + i)
                    .isbn("ABC" + i)
                    .category(BookCategory.ESSAY)
                    .build();
            if (i == 0) {
                book.changeStatus(BookStatus.REMOVED);
            } else if (i == 1) {
                book.changeStatus(BookStatus.LOST);
            } else if (i < 5) {
                book.changeStatus(BookStatus.RENTED);
            } else {
                book.changeStatus(BookStatus.RENTABLE);
            }
            books.add(book);
            bookRepository.save(book);
        }
        return books;
    }

    private Book createBook() {
        return Book.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .publishDate(LocalDate.of(2022, 1,1))
                .category(BookCategory.ESSAY)
                .status(BookStatus.RENTABLE)
                .build();
    }
}
