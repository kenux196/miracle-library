package org.kenux.miraclelibrary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.enums.BookStatus;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    @DisplayName("책은 제목, 저자, isbn 정보를 가지고 생성된다.")
    void test_Book_Basic() {
        Book book = createBook();

        assertThat(book.getTitle()).isEqualTo("제목");
        assertThat(book.getAuthor()).isEqualTo("저자");
        assertThat(book.getIsbn()).isEqualTo("isbn");
    }

    @Test
    @DisplayName("책은 대여 가능, 대여중, 파기됨 상태를 변경할 수 있다.")
    void test_Book_changeStatus() throws Exception {
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
                .build();
    }
}