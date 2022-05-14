package org.kenux.miraclelibrary.domain.book.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookInfo;
import org.kenux.miraclelibrary.domain.book.repository.BookInfoRepository;
import org.kenux.miraclelibrary.domain.book.repository.BookRepository;
import org.kenux.miraclelibrary.web.book.dto.request.BookAddRequest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @Mock
    BookInfoRepository bookInfoRepository;

    @InjectMocks
    BookService bookService;

    @Test
    @DisplayName("신규 도서 등록 - 도서정보와 책 등록 성공")
    void addNewBook() {
        // given
        BookAddRequest request = BookAddRequest.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .publishDate("2020-10-10")
                .category(BookCategory.ESSAY)
                .count(1)
                .build();

        BookInfo bookInfo = request.toEntity();
        ReflectionTestUtils.setField(bookInfo, "id", 1L);
        given(bookInfoRepository.save(any())).willReturn(bookInfo);

        // when
        Long result = bookService.addNewBook2(request.toEntity());

        // then
        assertThat(result).isEqualTo(1L);
    }


//    @Test
//    @DisplayName("전체 도서 목록 가져오기")
//    void getAllBooks() throws Exception {
//        // given
//        List<Book> books = createBookListForTest(10);
//        given(bookRepository.findAll()).willReturn(books);
//
//        // when
//        List<BookResponse> allBooks = bookService.getAllBooks();
//
//        // then
//        assertThat(allBooks).hasSize(10);
//
//    }
//
//    // TODO : 관리자, 매니저인 경우에만 가능하도록 테스트 변경   - sky 2022/01/22
//    @Test
//    @DisplayName("신규 도서 등록")
//    void addNewBook() throws Exception {
//        // given
//        Book book = createBookForTest();
//        BookAddRequest request = BookAddRequest.builder()
//                .title("title")
//                .author("author")
//                .isbn("isbn")
//                .publishDate("2020-10-10")
//                .category(BookCategory.ESSAY)
//                .build();
//        given(bookRepository.save(any())).willReturn(book);
//
//        // when
//        Long result = bookService.addNewBook(request);
//
//        // then
//        assertThat(result).isEqualTo(1L);
//    }
//
//    @Test
//    @DisplayName("검색필터를 이용해서 도서 조회")
//    void searchBookByFilter() throws Exception {
//        // given
//        BookSearchFilter bookSearchFilter = BookSearchFilter.builder()
//                .keyword("title")
//                .build();
//        Book book = createBookForTest();
//        given(bookRepository.findBookByFilter(any())).willReturn(Collections.singletonList(book));
//
//        // when
//        List<BookResponse> books = bookService.searchBookByFilter(bookSearchFilter);
//
//        // then
//        assertThat(books).isNotEmpty();
//
//        // verify
//        verify(bookRepository).findBookByFilter(any());
//    }
//
//    // TODO : 관리자, 매니저, 일반회원에 구분 테스트 추가   - sky 2022/01/22
//    @Test
//    @DisplayName("일반 회원의 도서검색 결과에는 파기/분실 상태 미포함")
//    void searchBookByFilter_검색결과_분실파기상태는_미포함() throws Exception {
//        // given
//        List<Book> foundBook = new ArrayList<>();
//        Book removedBook = Book.builder()
//                .status(BookStatus.REMOVED)
//                .build();
//        Book lostBook = Book.builder()
//                .status(BookStatus.LOST)
//                .build();
//        foundBook.add(removedBook);
//        foundBook.add(lostBook);
//
//        given(bookRepository.findBookByFilter(any())).willReturn(foundBook);
//
//        // when
//        List<BookResponse> books = bookService.searchBookByFilter(BookSearchFilter.builder().build());
//
//        // then
//        assertThat(books).isEmpty();
//    }
//
//    @Test
//    @DisplayName("신간 서적 목록 조회")
//    void getNewBooks() throws Exception {
//        // given
//        Book book = createBookForTest();
//        book.changeStatus(BookStatus.RENTABLE);
//        given(bookRepository.findNewBookWithinOneMonth(any())).willReturn(Collections.singletonList(book));
//
//        // when
//        List<NewBookResponse> newBooks = bookService.getNewBooks();
//
//        // then
//        assertThat(newBooks).hasSize(1);
//    }
//
//    @Test
//    @DisplayName("getBookDetail: id로 책 조회결과 없으면 BOOK_NOT_FOUND 예외처리")
//    void getBookDetail_검색결과없으면예외처리() throws Exception {
//        // given
//        given(bookRepository.findById(any())).willReturn(Optional.empty());
//
//        // when // then
//        assertThatThrownBy(() -> bookService.getBookDetail(1L))
//                .isInstanceOf(CustomException.class)
//                .hasMessage(BOOK_NOT_FOUND.getMessage());
//    }
//
//    @Test
//    @DisplayName("getBookDetail: 정상")
//    void getBookDetail_정상처리() throws Exception {
//        // given
//        final Book book = createBookForTest();
//        given(bookRepository.findById(any())).willReturn(Optional.of(book));
//
//        // when
//        final BookDetailResponse response = bookService.getBookDetail(1L);
//
//        // then
//        assertThat(response).isNotNull();
//        assertThat(response.getId()).isEqualTo(book.getId());
//        assertThat(response.getTitle()).isEqualTo(book.getTitle());
//        assertThat(response.getAuthor()).isEqualTo(book.getAuthor());
//        assertThat(response.getIsbn()).isEqualTo(book.getIsbn());
//        assertThat(response.getPublishDate()).isEqualTo("2022-01-01");
//    }
//
//    @Test
//    @DisplayName("도서 정보 업데이트")
//    void updateBook() throws Exception {
//        // given
//        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest();
//        bookUpdateRequest.setId(1L);
//        bookUpdateRequest.setTitle("title1");
//        bookUpdateRequest.setAuthor("author1");
//        bookUpdateRequest.setCategory(BookCategory.ECONOMY);
//        bookUpdateRequest.setContent("");
//        bookUpdateRequest.setIsbn("isbn-123");
//        bookUpdateRequest.setPublishDate("2022-01-19");
//        bookUpdateRequest.setCover(null);
//
//        final Book book = createBookForTest();
//        given(bookRepository.findById(any())).willReturn(Optional.of(book));
//
//        // when
//        Long updateId = bookService.updateBook(bookUpdateRequest);
//
//        // then
//        assertThat(updateId).isEqualTo(bookUpdateRequest.getId());
//        verify(bookRepository).findById(any());
//        assertThat(book.getTitle()).isEqualTo(bookUpdateRequest.getTitle());
//    }
}