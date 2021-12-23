package org.kenux.miraclelibrary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.enums.BookStatus;

import java.time.LocalDate;

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
    @DisplayName("책은 대출 가능 상태를 변경할 수 있다.")
    void test_isAvailableForRental() {
        // given
        Book book = createBook();

        // when
        book.changeStatus(BookStatus.AVAILABLE_FOR_RENTAL);

        // then
        assertThat(book.isAvailableForRental()).isTrue();
    }

    @Test
    @DisplayName("대여된 책은 대출 불가능해야 한다.")
    void test_BookRented_availableForRental_isFalse() throws Exception {
        // given
        Book book = createBook();
        LocalDate startDate = LocalDate.of(2021, 1, 1);

        // when
        book.changeStatus(BookStatus.RENTED);

        // then
        assertThat(book.isAvailableForRental()).isFalse();
    }

    @Test
    @DisplayName("파기된 책은 대출 불가능해야 한다.")
    void test_BookRemoved_notAvailableRental() throws Exception {
        // given
        Book book = createBook();

        // when
        book.changeStatus(BookStatus.REMOVED);

        // then
        assertThat(book.isAvailableForRental()).isFalse();
    }

    @Test
    @DisplayName("대여된 책은 대여 시작 날짜를 가진다.")
    void test_RentalStartDate_whenBookRented() throws Exception {
        // given
        Book book = createBook();
        LocalDate startDate = LocalDate.of(2021, 1, 1);

        // when
        book.beRented(startDate);

        // then
        assertThat(book.getRentalStartDate()).isEqualTo(startDate);
    }

    @Test
    @DisplayName("대여된 책은 2주뒤에 반납일이 된다.")
    void test_RentalEndDate_whenBookRented() throws Exception {
        // given
        Book book = createBook();
        LocalDate startDate = LocalDate.of(2021, 1, 1);
        LocalDate endDate = startDate.plusWeeks(2);

        // when
        book.beRented(startDate);

        // then
        assertThat(book.getRentalEndDate()).isEqualTo(endDate);

    }

    @Test
    @DisplayName("대여된 책은 대여 상태로 변경되어야 한다.")
    void test_RentedStatus_whenBookRented() {
        // given
        Book book = createBook();
        LocalDate startDate = LocalDate.of(2021, 1, 1);

        // when
        book.beRented(startDate);

        // then
        assertThat(book.isAvailableForRental()).isFalse();
    }

    private Book createBook() {
        return Book.builder()
                .title("제목")
                .author("저자")
                .isbn("isbn")
                .build();
    }
}