package org.kenux.miraclelibrary.domain.book.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.web.book.controller.dto.request.BookAddRequest;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;

import static org.assertj.core.api.Assertions.assertThat;

class BookAddRequestTest {

    private BookAddRequest bookAddRequest;

    @BeforeEach
    void setup() {
        bookAddRequest = createBookRegisterRequest();
    }

    @Test
    @DisplayName("Entity 변환")
    void toEntity() {
        Book book = bookAddRequest.toEntity();

        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(bookAddRequest.getTitle());
        assertThat(book.getAuthor()).isEqualTo(bookAddRequest.getAuthor());
        assertThat(book.getIsbn()).isEqualTo(bookAddRequest.getIsbn());
        assertThat(book.getStatus()).isEqualTo(BookStatus.RENTABLE);
        assertThat(book.getPublishDate()).isNotNull();
    }

    private BookAddRequest createBookRegisterRequest() {
        return BookAddRequest.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .publishDate("2020-12-11")
                .build();
    }

}