package org.kenux.miraclelibrary.domain.book.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.book.controller.request.BookRegisterRequest;
import org.kenux.miraclelibrary.domain.book.controller.request.BookSearchFilter;
import org.kenux.miraclelibrary.domain.book.controller.request.BookUpdateRequest;
import org.kenux.miraclelibrary.domain.book.controller.response.BookDetailResponse;
import org.kenux.miraclelibrary.domain.book.controller.response.BookResponse;
import org.kenux.miraclelibrary.domain.book.controller.response.NewBookResponse;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.kenux.miraclelibrary.domain.book.repository.BookRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.kenux.miraclelibrary.global.exception.ErrorCode.BOOK_NOT_FOUND;
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
        List<BookResponse> books = bookService.searchBookByFilter(bookSearchFilter);

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
        List<BookResponse> books = bookService.searchBookByFilter(BookSearchFilter.builder().build());

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
        List<NewBookResponse> newBooks = bookService.getNewBooks();

        // then
        assertThat(newBooks).hasSize(1);
    }

    @Test
    @DisplayName("getBookDetail: id로 책 조회결과 없으면 BOOK_NOT_FOUND 예외처리")
    void getBookDetail_검색결과없으면예외처리() throws Exception {
        // given
        given(bookRepository.findById(any())).willReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> bookService.getBookDetail(1L))
                .isInstanceOf(CustomException.class)
                .hasMessage(BOOK_NOT_FOUND.getMessage());
    }    

    @Test
    @DisplayName("getBookDetail: 정상")
    void getBookDetail_정상처리() throws Exception {
        // given
        final Book book = createBookForTest();
        given(bookRepository.findById(any())).willReturn(Optional.of(book));

        // when
        final BookDetailResponse response = bookService.getBookDetail(1L);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(book.getId());
        assertThat(response.getTitle()).isEqualTo(book.getTitle());
        assertThat(response.getAuthor()).isEqualTo(book.getAuthor());
        assertThat(response.getIsbn()).isEqualTo(book.getIsbn());
        assertThat(response.getPublicationDate()).isEqualTo("2022-01-01");
    }

    @Test
    @DisplayName("책 업데이트: 변경 항목이 없는 경우")
    void updateBook_변경항목없는경우() throws Exception {
        // given
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest();
        bookUpdateRequest.setId(1L);

        final Book expectedBook = createBookForTest();
        given(bookRepository.findById(any())).willReturn(Optional.of(expectedBook));

        // when
        Book updatedBook = bookService.updateBook(bookUpdateRequest);

        // then
        assertThat(updatedBook.getId()).isEqualTo(expectedBook.getId());
        assertThat(updatedBook.getTitle()).isEqualTo(expectedBook.getTitle());
        assertThat(updatedBook.getAuthor()).isEqualTo(expectedBook.getAuthor());
        assertThat(updatedBook.getIsbn()).isEqualTo(expectedBook.getIsbn());
        assertThat(updatedBook.getPublicationDate()).isEqualTo(expectedBook.getPublicationDate());
        assertThat(updatedBook.getContent()).isEqualTo(expectedBook.getContent());
        assertThat(updatedBook.getCover()).isEqualTo(expectedBook.getCover());
        assertThat(updatedBook.getCategory()).isEqualTo(expectedBook.getCategory());
    }

    @Test
    @DisplayName("책 업데이트: 전체 항목")
    void updateBook_정상인경우() throws Exception {
        // given
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest();
        bookUpdateRequest.setId(1L);
        bookUpdateRequest.setTitle("title1");
        bookUpdateRequest.setAuthor("author1");
        bookUpdateRequest.setIsbn("isbn1");
        bookUpdateRequest.setPublicationDate(LocalDate.of(2022, 1, 24));
        bookUpdateRequest.setCategory(BookCategory.FICTION);
        bookUpdateRequest.setContent("content");
        bookUpdateRequest.setCover("cover11");

        Book expectedBook = Book.builder()
                .id(bookUpdateRequest.getId())
                .title(bookUpdateRequest.getTitle())
                .author(bookUpdateRequest.getAuthor())
                .isbn(bookUpdateRequest.getIsbn())
                .publicationDate(bookUpdateRequest.getPublicationDate())
                .category(bookUpdateRequest.getCategory())
                .content(bookUpdateRequest.getContent())
                .cover(bookUpdateRequest.getCover())
                .status(BookStatus.RENTABLE)
                .build();

        final Book book = createBookForTest();
        given(bookRepository.findById(any())).willReturn(Optional.ofNullable(book));

        // when
        Book updatedBook = bookService.updateBook(bookUpdateRequest);

        // then
        assertThat(updatedBook.getId()).isEqualTo(expectedBook.getId());
        assertThat(updatedBook.getTitle()).isEqualTo(expectedBook.getTitle());
        assertThat(updatedBook.getAuthor()).isEqualTo(expectedBook.getAuthor());
        assertThat(updatedBook.getIsbn()).isEqualTo(expectedBook.getIsbn());
        assertThat(updatedBook.getPublicationDate()).isEqualTo(expectedBook.getPublicationDate());
        assertThat(updatedBook.getContent()).isEqualTo(expectedBook.getContent());
        assertThat(updatedBook.getCover()).isEqualTo(expectedBook.getCover());
        assertThat(updatedBook.getCategory()).isEqualTo(expectedBook.getCategory());
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