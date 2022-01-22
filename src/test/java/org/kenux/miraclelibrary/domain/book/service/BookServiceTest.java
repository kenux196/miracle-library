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

    // TODO : 관리자, 매니저인 경우에만 가능하도록 테스트 변경   - sky 2022/01/22
    @Test
    @DisplayName("신규 도서 등록")
    void registerNewBook() throws Exception {
        // given
        Book book = createBookForTest();
        given(bookRepository.save(any())).willReturn(book);

        // when
        Long result = bookService.registerNewBook(new BookRegisterRequest());

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    @DisplayName("검색필터를 이용해서 도서 조회")
    void searchBookByFilter() throws Exception {
        // given
        BookSearchFilter bookSearchFilter = BookSearchFilter.builder()
                .keyword("title")
                .build();
        Book book = createBookForTest();
        given(bookRepository.findBookByFilter(any())).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.searchBookByFilter(bookSearchFilter);

        // then
        assertThat(books).isNotEmpty();

        // verify
        verify(bookRepository).findBookByFilter(any());
    }

    // TODO : 관리자, 매니저, 일반회원에 구분 테스트 추가   - sky 2022/01/22
    @Test
    @DisplayName("일반 회원의 도서검색 결과에는 파기/분실 상태 미포함")
    void searchBookByFilter_검색결과_분실파기상태는_미포함() throws Exception {
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
        List<Book> books = bookService.searchBookByFilter(BookSearchFilter.builder().build());

        // then
        assertThat(books).isEmpty();
    }

    @Test
    @DisplayName("신간 서적 목록 조회")
    void getNewBooks() throws Exception {
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