package org.kenux.miraclelibrary.domain.book.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookInfo;
import org.kenux.miraclelibrary.global.config.QueryDslConfig;
import org.kenux.miraclelibrary.web.book.dto.request.BookSearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(QueryDslConfig.class)
class BookInfoRepositoryTest {

    @Autowired
    BookInfoRepository bookInfoRepository;

    @AfterEach
    void afterEach() {
        bookInfoRepository.deleteAll();
    }

    @Test
    @DisplayName("BookInfo 저장")
    void save() {
        // given
        BookInfo save = createAndSaveBookInfo();

        // when
        Optional<BookInfo> result = bookInfoRepository.findById(save.getId());

        // then
        assertThat(result).isNotEmpty().contains(save);
    }

    @Test
    @DisplayName("도서 검색 필터: 책 제목에 키워드가 포함된 경우 검색 성공")
    void searchBookInfoByFilter_Keyword_Title_Test() {
        // given
        createAndSaveBookInfo();
        BookSearchFilter filter = BookSearchFilter.builder()
                .keyword("제목")
                .build();

        // when
        List<BookInfo> result = bookInfoRepository.findBookByFilter(filter);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("도서 검색 필터: 작가명에 키워드가 포함된 경우 검색 성공")
    void searchBookInfoByFilter_Keyword_Author_Test() {
        // given
        createAndSaveBookInfo();
        BookSearchFilter filter = BookSearchFilter.builder()
                .keyword("저자")
                .build();

        // when
        List<BookInfo> result = bookInfoRepository.findBookByFilter(filter);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("도서 검색 필터: 제목, 작가 검색 결과가 없는 경우, 결과 0")
    void searchBookInfoByFilter_Keyword_NotFound_Test() {
        // given
        createAndSaveBookInfo();
        BookSearchFilter filter = BookSearchFilter.builder()
                .keyword("오브젝트")
                .build();

        // when
        List<BookInfo> result = bookInfoRepository.findBookByFilter(filter);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("도서 검색 필터: 카테고리 검색 성공")
    void searchBookInfoByFilter_Category_Found_Test() {
        // given
        createAndSaveBookInfo();
        BookSearchFilter filter = BookSearchFilter.builder()
                .category(BookCategory.IT)
                .build();

        // when
        List<BookInfo> result = bookInfoRepository.findBookByFilter(filter);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("도서 검색 필터: 카테고리 검색 결과 없음")
    void searchBookInfoByFilter_Category_NotFound_Test() {
        // given
        createAndSaveBookInfo();
        BookSearchFilter filter = BookSearchFilter.builder()
                .category(BookCategory.ESSAY)
                .build();

        // when
        List<BookInfo> result = bookInfoRepository.findBookByFilter(filter);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("도서 검색 시, 보유 도서 목록도 조회 가능")
    void searchBookInfoWithBookList_Test() {
        // given
        BookInfo bookInfo = createBookInfo();
        bookInfo.addBook(Book.createNewBook());
        BookInfo save = bookInfoRepository.save(bookInfo);

        // when
        Optional<BookInfo> result = bookInfoRepository.findById(save.getId());

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getBooks()).hasSize(1);
    }

    @Test
    @DisplayName("도서 검색: 한달 이내에 등록된 신규 도서 조회")
    void findNewBookWithinOneMonth() throws Exception {
        // given
        final BookInfo oldBook = BookInfo.builder()
                .title("book1")
                .author("author1")
                .isbn("123")
                .category(BookCategory.IT)
                .publishDate(LocalDate.of(2022, 2, 1))
                .build();
        ReflectionTestUtils.setField(oldBook, "createDate", LocalDateTime.now().minusMonths(2));
        bookInfoRepository.save(oldBook);

        final BookInfo newBook = BookInfo.builder()
                .title("book2")
                .author("author2")
                .isbn("123")
                .category(BookCategory.IT)
                .publishDate(LocalDate.of(2022, 3, 1))
                .build();
        ReflectionTestUtils.setField(newBook, "createDate", LocalDateTime.now().minusDays(14));
        bookInfoRepository.save(newBook);
        // when
        final List<BookInfo> newBookWithinOneMonth =
                bookInfoRepository.findNewAddedBookWithinOneMonth();

        // then
        assertThat(newBookWithinOneMonth).hasSize(1);
    }

    private BookInfo createAndSaveBookInfo() {
        BookInfo bookInfo = createBookInfo();
        return bookInfoRepository.save(bookInfo);
    }

    private BookInfo createBookInfo() {
        return BookInfo.builder()
                .title("제목")
                .isbn("isbn")
                .author("저자")
                .publishDate(LocalDate.now())
                .category(BookCategory.IT)
                .build();
    }
}