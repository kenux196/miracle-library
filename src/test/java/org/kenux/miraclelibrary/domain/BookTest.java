package org.kenux.miraclelibrary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    @DisplayName("책은 제목, 저자, isbn, 보유 수를 가져야 한다.")
    void test_Book_Basic() {
        final Book book = new Book("책제목", "저자", "isbn", 3);

        assertThat(book.getTitle()).isEqualTo("책제목");
        assertThat(book.getAuthor()).isEqualTo("저자");
        assertThat(book.getIsbn()).isEqualTo("isbn");
        assertThat(book.getAmount()).isEqualTo(3);
    }

    @Test
    @DisplayName("책이 1권 대여되면, 보유수가 변한다.")
    void test_decreaseBookAmount_whenBookRented() {
        final Book book = new Book("책제목", "저자", "isbn", 3);

        book.rented();

        assertThat(book.getAmount()).isEqualTo(2);
    }

    @Test
    @DisplayName("책이 반납이 되면, 보유수가 증가한다.")
    void test_increaseBookAmount_WhenBookReturned() {
        final Book book = new Book("책제목", "저자", "isbn", 2);

        book.returned();

        assertThat(book.getAmount()).isEqualTo(3);
    }

    @Test
    @DisplayName("책의 보유수가 0이면 대출 불가 상태이다.")
    void test_isAvailableForRental() {
        final Book book = new Book("책제목", "저자", "isbn", 0);

        final boolean availableForRental = book.isAvailableForRental();

        assertThat(availableForRental).isFalse();
    }
}