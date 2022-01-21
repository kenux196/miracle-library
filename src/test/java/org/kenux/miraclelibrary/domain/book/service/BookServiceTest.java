package org.kenux.miraclelibrary.domain.book.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.kenux.miraclelibrary.domain.book.dto.BookRegisterRequest;
import org.kenux.miraclelibrary.domain.book.dto.BookSearchFilter;
import org.kenux.miraclelibrary.domain.book.repository.BookRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    @Test
    @DisplayName("새로운 책을 등록한다.")
    void test_register_newBook() throws Exception {
        // given
        Book book = createBookForTest();
        given(bookRepository.save(any())).willReturn(book);

        // when
        Long result = bookService.registerNewBook(new BookRegisterRequest());

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    @DisplayName("제목 혹은 저자에 해당하는 키워드를 통해 책을 검색한다.")
    void test_searchBookByTitle() throws Exception {
        // given
        Book book = createBookForTest();
        given(bookRepository.findAllByKeyword(any())).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.searchBook("title");

        // then
        assertThat(books).isNotEmpty();
    }

    @Test
    @DisplayName("도서 필터 검색")
    void searchBookByFilterOnlyKeyword() throws Exception {
        // given
        BookSearchFilter bookSearchFilter = new BookSearchFilter();
        bookSearchFilter.setKeyword("title");

        Book book = createBookForTest();
        given(bookRepository.findBookByFilter(any())).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.searchBookByFilter(bookSearchFilter);

        // then
        assertThat(books).isNotEmpty();

        // verify
        verify(bookRepository).findBookByFilter(any());
    }

    @Test
    @DisplayName("회원의 도서 검색 결과에는 파기, 분실 상태의 책은 없어야 한다.")
    void searchBookThatIsNotRemovedBook() throws Exception {
        // given
        List<Book> foundBook = new ArrayList<>();
        Book removedBook = Book.builder()
                .status(BookStatus.REMOVED)
                .build();
        Book lostBook = Book.builder()
                .status(BookStatus.LOST)
                .build();
        foundBook.add(removedBook);
        foundBook.add(lostBook);

        given(bookRepository.findBookByFilter(any())).willReturn(foundBook);

        // when
        List<Book> books = bookService.searchBookByFilter(new BookSearchFilter());

        // then
        assertThat(books).isEmpty();
    }

    @Test
    @DisplayName("키워드가 입력되지 않으면 전체 책 리스트를 가져온다.")
    void test_getAllBooks_whenNotExistKeyword() throws Exception {
        // given
        Book book = createBookForTest();
        given(bookRepository.findAllByKeyword(null)).willReturn(Collections.singletonList(book));

        // when
        final List<Book> books = bookService.searchBook(null);

        // then
        assertThat(books).isNotEmpty();
    }

    @Test
    @DisplayName("검색한 책의 대출 가능 여부를 확인할 수 있어야 한다.")
    void test_isAvailableRent() throws Exception {
        // given
        Book book = createBookForTest();
        book.changeStatus(BookStatus.RENTED);
        given(bookRepository.findAllByKeyword(any())).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.searchBook("title");

        // then
        assertThat(books.get(0).getStatus()).isEqualTo(BookStatus.RENTED);
    }

    @Test
    void 신간_서적_리스트_가져오기() throws Exception {
        // given
        Book book = createBookForTest();
        book.changeStatus(BookStatus.RENTABLE);
        given(bookRepository.findNewBookWithinOneMonth(any())).willReturn(Collections.singletonList(book));
        
        // when
        List<Book> newBooks = bookService.getNewBooks();

        // then
        assertThat(newBooks).hasSize(1);
    }


    private List<Book> createBookListForTest(int count) {
        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            final Book book = Book.builder()
                    .id((long) i)
                    .title("book" + i)
                    .author("author" + i)
                    .isbn("ABC" + i)
                    .publicationDate(LocalDate.of(2022, 1, 1))
                    .build();
            if (i % 2 == 1) {
                book.changeStatus(BookStatus.RENTED);
            } else {
                book.changeStatus(BookStatus.RENTABLE);
            }
            bookList.add(book);
        }
        return bookList;
    }

    private Book createBookForTest() {
        return Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .category(BookCategory.ESSAY)
                .status(BookStatus.RENTABLE)
                .publicationDate(LocalDate.of(2022, 1, 1))
                .build();
    }

}