package org.kenux.miraclelibrary.domain.book.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookInfo;
import org.kenux.miraclelibrary.domain.book.domain.BookItem;
import org.kenux.miraclelibrary.domain.book.repository.BookInfoRepository;
import org.kenux.miraclelibrary.domain.book.repository.BookRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.kenux.miraclelibrary.web.book.dto.request.BookAddRequest;
import org.kenux.miraclelibrary.web.book.dto.request.BookSearchFilter;
import org.kenux.miraclelibrary.web.book.dto.request.BookUpdateRequest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.kenux.miraclelibrary.global.exception.ErrorCode.BOOK_NOT_FOUND;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookItemServiceTest {

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
                .amount(1)
                .build();

        BookInfo bookInfo = request.toEntity();
        ReflectionTestUtils.setField(bookInfo, "id", 1L);
        given(bookInfoRepository.save(any())).willReturn(bookInfo);

        // when
        Long result = bookService.addNewBook(request.toEntity());

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    @DisplayName("전체 도서 목록 가져오기")
    void getAllBooks() throws Exception {
        // given
        List<BookInfo> bookInfoList = createBookListForTest();
        given(bookInfoRepository.findAll()).willReturn(bookInfoList);

        // when
        List<BookInfo> allBooks = bookService.getAllBooks();

        // then
        assertThat(allBooks).hasSize(bookInfoList.size());
    }

    @Test
    @DisplayName("검색필터를 이용해서 도서 조회")
    void searchBookByFilter() throws Exception {
        // given
        BookSearchFilter bookSearchFilter = BookSearchFilter.builder()
                .keyword("title")
                .build();

        List<BookInfo> bookInfoList = createBookListForTest();
        given(bookInfoRepository.findBookByFilter(any())).willReturn(bookInfoList);

        // when
        List<BookInfo> books = bookService.searchBookByFilter(bookSearchFilter);

        // then
        assertThat(books).isNotEmpty();

        // verify
        verify(bookInfoRepository).findBookByFilter(any());
    }
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
    @Test
    @DisplayName("신간 서적 목록 조회")
    void getNewBooks() throws Exception {
        // given
        List<BookInfo> bookInfos = createBookListForTest();
        given(bookInfoRepository.findNewBookWithinOneMonth()).willReturn(bookInfos);

        // when
        List<BookInfo> newBooks = bookService.getNewBooks();

        // then
        assertThat(newBooks).hasSize(3);
        verify(bookInfoRepository).findNewBookWithinOneMonth();
    }

    @Test
    @DisplayName("getBookDetail: id로 책 조회결과 없으면 BOOK_NOT_FOUND 예외처리")
    void getBookDetail_검색결과없으면예외처리() throws Exception {
        // given
        given(bookInfoRepository.findById(any())).willReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> bookService.getBookDetail(1L))
                .isInstanceOf(CustomException.class)
                .hasMessage(BOOK_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("getBookDetail: 정상")
    void getBookDetail_정상처리() throws Exception {
        // given
        final BookInfo bookInfo = createBookInfo(1L, "제목3", 3, LocalDate.now());
        given(bookInfoRepository.findById(any())).willReturn(Optional.ofNullable(bookInfo));

        // when
        final BookInfo result = bookService.getBookDetail(1L);

        // then
        assertThat(result).isNotNull();
    }


    @Test
    @DisplayName("도서 정보 업데이트")
    void updateBook() throws Exception {
        // given
        BookUpdateRequest bookUpdateRequest = new BookUpdateRequest();
        bookUpdateRequest.setId(1L);
        bookUpdateRequest.setTitle("title1");
        bookUpdateRequest.setAuthor("author1");
        bookUpdateRequest.setCategory(BookCategory.ECONOMY);
        bookUpdateRequest.setContent("");
        bookUpdateRequest.setIsbn("isbn-123");
        bookUpdateRequest.setPublishDate("2022-01-19");
        bookUpdateRequest.setCover(null);

        final BookInfo bookInfo = createBookInfo(1L, "제목3", 3, LocalDate.now());
        given(bookInfoRepository.findById(any())).willReturn(Optional.of(bookInfo));

        // when
        Long updateId = bookService.updateBook(1L, bookUpdateRequest.toEntity());

        // then
        assertThat(updateId).isEqualTo(bookUpdateRequest.getId());
        assertThat(bookInfo.getTitle()).isEqualTo(bookUpdateRequest.getTitle());
    }

    private List<BookInfo> createBookListForTest() {
        List<BookInfo> bookInfoList = new ArrayList<>();
        bookInfoList.add(createBookInfo(1L,"제목1", 1, LocalDate.of(2022, 1, 1)));
        bookInfoList.add(createBookInfo(2L, "제목2", 2, LocalDate.of(2022, 2, 1)));
        bookInfoList.add(createBookInfo(3L, "제목3", 3, LocalDate.now().minusDays(10)));
        return bookInfoList;
    }

    private BookInfo createBookInfo(Long id, String title, int count, LocalDate publishDate) {
        BookInfo bookInfo = BookInfo.builder()
                .title(title)
                .isbn("isbn")
                .author("저자")
                .publishDate(publishDate)
                .category(BookCategory.IT)
                .build();
        ReflectionTestUtils.setField(bookInfo, "id", id);
        for (int i = 0; i < count; i++) {
            bookInfo.addBook(BookItem.createNewBook());
        }
        return bookInfo;
    }
}