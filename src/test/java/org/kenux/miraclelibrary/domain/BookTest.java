package org.kenux.miraclelibrary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.enums.BookStatus;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    private Book createNewBook() {
        final Book book = new Book("책제목", "저자", "isbn");
        book.changeStatus(BookStatus.AVAILABLE_FOR_RENTAL);
        return book;
    }

    @Test
    @DisplayName("책은 제목, 저자, isbn, 대출상태를 가져야 한다.")
    void test_Book_Basic() {
        final Book book = new Book("책제목", "저자", "isbn");

        assertThat(book.getTitle()).isEqualTo("책제목");
        assertThat(book.getAuthor()).isEqualTo("저자");
        assertThat(book.getIsbn()).isEqualTo("isbn");
    }

    @Test
    @DisplayName("새로운 책은 항상 상태가 대여 가능이다.")
    void test_BookStatus_alwaysAvailableForRental_When_createNewBook() {
        final Book book = createNewBook();

        assertThat(book.getStatus()).isEqualTo(BookStatus.AVAILABLE_FOR_RENTAL);
    }

    @Test
    @DisplayName("책은 자신의 대여 상태를 변경할 수 있다.")
    void test_ChangeBookStatus() {
        final Book book = createNewBook();

        book.changeStatus(BookStatus.BEING_RENTAL);

        assertThat(book.getStatus()).isEqualTo(BookStatus.BEING_RENTAL);
    }

    @Test
    @DisplayName("책의 대여 가능 여부를 조회할 수 있다.")
    void test_isAvailableForRental() {
        final Book book = createNewBook();

        final boolean availableForRental = book.isAvailableForRental();

        assertThat(availableForRental).isTrue();
    }
}