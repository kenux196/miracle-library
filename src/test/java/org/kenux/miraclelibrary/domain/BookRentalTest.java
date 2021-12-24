package org.kenux.miraclelibrary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookRentalTest {

    @Test
    @DisplayName("도서 대출 정보에는 책과 회원 정보가 포함된다.")
    void test_bookRental_hasBook() throws Exception {
        Book book = Book.builder().build();
        Member member = Member.builder().build();
        BookRental bookRental = BookRental.builder()
                .book(book)
                .member(member)
                .build();

        assertThat(bookRental.getBook()).isNotNull();
        assertThat(bookRental.getMember()).isNotNull();
    }

    @Test
    @DisplayName("도서 대출 정보에는 대출 시작일이 있어야 한다.")
    void test_bookRental_hasStartDate() throws Exception {
        // given
        LocalDate startDate = LocalDate.of(2021, 1, 1);

        // when
        BookRental bookRental = BookRental.builder()
                .rentalStartDate(startDate)
                .build();

        // then
        assertThat(bookRental.getRentalStartDate()).isNotNull();
        assertThat(bookRental.getRentalStartDate()).isEqualTo(startDate);
    }

    @Test
    @DisplayName("도서 대출 정보를 통해서 반납 예정일을 알 수 있어야 한다.")
    void test_bookRental_getRentalEndDate() throws Exception {
        // given
        LocalDate startDate = LocalDate.of(2021, 1, 1);
        BookRental bookRental = BookRental.builder()
                .rentalStartDate(startDate)
                .build();

        // when
        LocalDate returnDate = bookRental.getRentalEndDate();

        // then
        assertThat(returnDate).isEqualTo(startDate.plusWeeks(2));
    }

    @Test
    @DisplayName("도서 대출 정보에는 책이 반납된 날짜가 있어야 한다.")
    void test_bookRental_getReturnDate() throws Exception {
        // given
        LocalDate returnDate = LocalDate.of(2021, 1, 10);
        BookRental bookRental = BookRental.builder().build();

        // when
        bookRental.setReturnDate(returnDate);

        // then
        assertThat(bookRental.getReturnDate()).isEqualTo(returnDate);
    }

    @Test
    @DisplayName("도서 대출 정보를 통해 연체 상태를 알 수 있다.")
    void test_bookRental_isOverDue() {
        // given
        LocalDate rentalStartDate = LocalDate.of(2021, 1, 10);
        BookRental bookRental = BookRental.builder()
                .rentalStartDate(rentalStartDate)
                .build();

        assertThat(bookRental.isOverDue(rentalStartDate.plusWeeks(1))).isFalse();
        assertThat(bookRental.isOverDue(rentalStartDate.plusWeeks(2))).isFalse();
        assertThat(bookRental.isOverDue(rentalStartDate.plusWeeks(3))).isTrue();
    }

}