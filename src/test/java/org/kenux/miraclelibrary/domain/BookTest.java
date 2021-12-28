package org.kenux.miraclelibrary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.enums.BookStatus;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    @DisplayName("책은 제목, 저자, isbn, 등록일자를 가진다.")
    void test_create_book() {
        Book book = createBook();

        assertThat(book.getTitle()).isEqualTo("제목");
        assertThat(book.getAuthor()).isEqualTo("저자");
        assertThat(book.getIsbn()).isEqualTo("isbn");
        assertThat(book.getCreatedDate()).isEqualTo(createDate());
    }

    @Test
    @DisplayName("책은 대여 가능, 대여중, 파기됨 상태를 변경할 수 있다.")
    void test_change_bookStatus() throws Exception {
        Book book = createBook();
        book.changeStatus(BookStatus.AVAILABLE);
        assertThat(book.getStatus()).isEqualTo(BookStatus.AVAILABLE);

        book.changeStatus(BookStatus.RENTED);
        assertThat(book.getStatus()).isEqualTo(BookStatus.RENTED);
    }

    private Book createBook() {
        return Book.builder()
                .title("제목")
                .author("저자")
                .isbn("isbn")
                .createdDate(createDate())
                .build();
    }

    private LocalDate createDate() {
        return LocalDate.of(2021, 1, 1);
    }
}