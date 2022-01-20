package org.kenux.miraclelibrary.domain.book.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.kenux.miraclelibrary.global.config.QueryDslConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    @DisplayName("책 정보 등록")
    void test_BookRepository_hasBookData() {
        final Book book = createBook();

        final Book save = bookRepository.save(book);

        assertThat(save.getId()).isNotNull();
        assertThat(save).isSameAs(book);
        assertThat(save.getId()).isEqualTo(book.getId());
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

    @Test
    @DisplayName("전체 리스트를 가져온다.")
    void test_findAllBooks_whenNotExistKeyword() throws Exception {
        // given
        final Book book = createBook();
        bookRepository.save(book);

        // when
        final List<Book> result = bookRepository.findAllByKeyword(null);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("전체 책 보유 수 가져오기")
    void test_countAllBooks() throws Exception {
        // given
        final List<Book> bookList = createBookList();

        // when
        final long count = bookRepository.count();

        // then
        assertThat(count).isEqualTo(bookList.size());
    }

    @Test
    @DisplayName("전체 책에서 대여된 책 수 가져오기")
    void test_countOfRentedBook() throws Exception {
        // given
        final List<Book> bookList = createBookList();
        final long count = bookList.stream()
                .filter(book -> book.getStatus().equals(BookStatus.RENTED))
                .count();

        // when
        final int rentedBookCount = bookRepository.countByStatus(BookStatus.RENTED);

        // then
        assertThat(rentedBookCount).isEqualTo(count);
    }

    @Test
    @DisplayName("전체 책에서 대여가능한 책 수 가져오기")
    void test_countOfRentableBook() throws Exception {
        // given
        final List<Book> bookList = createBookList();
        final long count = bookList.stream()
                .filter(book -> book.getStatus().equals(BookStatus.RENTABLE))
                .count();

        // when
        final int rentedBookCount = bookRepository.countByStatus(BookStatus.RENTABLE);

        // then
        assertThat(rentedBookCount).isEqualTo(count);
    }

    @Test
    void 한달_이내에_출간된_신간_도서_조회() throws Exception {
        // given
        final Book book1 = Book.builder()
                .title("book1")
                .author("author1")
                .isbn("123")
                .status(BookStatus.RENTABLE)
                .publicationDate(LocalDate.of(2020, 1, 1))
                .build();
        bookRepository.save(book1);
        final Book book2 = Book.builder()
                .title("book2")
                .author("author2")
                .isbn("123")
                .status(BookStatus.RENTABLE)
                .publicationDate(LocalDate.of(2022, 1, 1))
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

    @Test
    void sampleTest() {
        final Book book = createBook();
        ReflectionTestUtils.setField(
                book,
                "createDate",
                LocalDateTime.of(2021, 12, 30, 1, 1));
        final Book save = bookRepository.save(book);

        System.out.println("save.getCreateDate() = " + save.getCreateDate());
    }


    private List<Book> createBookList() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final Book book = Book.builder()
                    .title("book" + i)
                    .author("author" + i)
                    .isbn("ABC" + i)
                    .build();
            if (i % 2 == 1) {
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
                .publicationDate(LocalDate.of(2022, 1,1))
                .status(BookStatus.RENTABLE)
                .build();
    }
}
