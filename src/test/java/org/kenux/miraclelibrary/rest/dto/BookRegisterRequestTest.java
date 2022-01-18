package org.kenux.miraclelibrary.rest.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.enums.BookStatus;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookRegisterRequestTest {

    private BookRegisterRequest bookRegisterRequest;

    @BeforeEach
    void setup() {
        bookRegisterRequest = createBookRegisterRequest();
    }

    @Test
    @DisplayName("Book Entity 로 변환")
    void test_toEntity() {
        Book book = bookRegisterRequest.toEntity();

        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(bookRegisterRequest.getTitle());
        assertThat(book.getAuthor()).isEqualTo(bookRegisterRequest.getAuthor());
        assertThat(book.getIsbn()).isEqualTo(bookRegisterRequest.getIsbn());
    }

    @Test
    @DisplayName("book entity 로 변환 시, 책의 대여 상태가 초기화된다.")
    void test_hasBookStatus_toEntity() throws Exception {
        // given
        // when
        Book book = bookRegisterRequest.toEntity();

        // then
        assertThat(book.getStatus()).isEqualTo(BookStatus.RENTABLE);
    }

    @Test
    @DisplayName("book entity 로 변환 시, 책의 출간 날짜를 가진다.")
    void test_hasBookCreateDate_toEntity() throws Exception {
        // given
        // when
        Book book = bookRegisterRequest.toEntity();

        // then
        assertThat(book.getPublicationDate()).isNotNull();
    }

    private BookRegisterRequest createBookRegisterRequest() {
        return BookRegisterRequest.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .publicationDate(LocalDate.of(2022, 1, 1))
                .build();
    }

}