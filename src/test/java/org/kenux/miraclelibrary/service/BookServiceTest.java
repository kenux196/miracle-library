package org.kenux.miraclelibrary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.enums.BookStatus;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.exception.ErrorCode;
import org.kenux.miraclelibrary.repository.BookRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
        Long result = bookService.registerNewBook(book);

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    @DisplayName("제목으로 책을 검색한다.")
    void test_searchBookByTitle() throws Exception {
        // given
        Book book = createBookForTest();

        // when

        // then
    }

    @Test
    @DisplayName("작가로 책을 검색한다.")
    void test_searchBookByAuthor() throws Exception {
        // given

        // when

        // then
    }

    @Test
    @DisplayName("제목 혹은 작가로 책을 검색한다.")
    void test_searchBookByTitleOrAuthor() throws Exception {
        // given
        // when

        // then
    }

    @Test
    @DisplayName("검색한 책의 대출 가능 여부를 확인할 수 있어야 한다.")
    void test_isAvailableRent() throws Exception {
        // given

        // when

        // then
    }

    private Book createBookForTest() {
        return Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .createDate(LocalDate.of(2021, 1, 1))
                .build();
    }

}