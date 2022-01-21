package org.kenux.miraclelibrary.domain.book.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookRegisterRequestTest {

    private BookRegisterRequest bookRegisterRequest;

    @BeforeEach
    void setup() {
        bookRegisterRequest = createBookRegisterRequest();
    }

    @Test
    @DisplayName("Book Entity 로의 변환")
    void test_toEntity() {
        Book book = bookRegisterRequest.toEntity();

        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo(bookRegisterRequest.getTitle());
        assertThat(book.getAuthor()).isEqualTo(bookRegisterRequest.getAuthor());
        assertThat(book.getIsbn()).isEqualTo(bookRegisterRequest.getIsbn());
        assertThat(book.getStatus()).isEqualTo(BookStatus.RENTABLE);
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