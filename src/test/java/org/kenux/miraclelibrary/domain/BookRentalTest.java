package org.kenux.miraclelibrary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class BookRentalTest {

    @Test
    @DisplayName("도서 대출 정보에는 책, 멤버, 대출시작일 포함된다.")
    void test_bookRental_Basic() throws Exception {
        Member member = Member.builder().build();
        BookRental bookRental = BookRental.builder()
                .book(new Book())
                .member(member)
                .startDate(LocalDateTime.now())
                .build();

        assertThat(bookRental.getBook()).isNotNull();
        assertThat(bookRental.getMember()).isNotNull();
        assertThat(bookRental.getStartDate()).isNotNull();
    }

    @Test
    @DisplayName("대출 시작 날짜를 기준으로 반납일이 계산되어야 한다.")
    void test_bookRental_getRentalEndDate() throws Exception {
        // given
        LocalDateTime startDate = LocalDateTime.of(2021, 1, 1, 13, 00, 00);
        BookRental bookRental = BookRental.builder()
                .book(new Book())
                .startDate(startDate)
                .build();

        // when
        LocalDate returnDate = bookRental.getEndDate();

        // then
        assertThat(returnDate).isEqualTo(startDate.plusWeeks(2).toLocalDate());
    }

    @Test
    @DisplayName("도서 대출 정보에는 책이 반납된 날짜가 있어야 한다.")
    void test_bookRental_getReturnDate() throws Exception {
        // given
        LocalDateTime returnDate = LocalDateTime.of(2021, 1, 10, 11, 00, 00);
        BookRental bookRental = BookRental.builder()
                .startDate(LocalDateTime.now())
                .book(new Book())
                .build();

        // when
        bookRental.setReturnDate(returnDate);

        // then
        assertThat(bookRental.getReturnDate()).isEqualTo(returnDate);
    }

    @Test
    @DisplayName("도서 대출 정보를 통해 연체 상태를 알 수 있다.")
    void test_bookRental_isOverDue() {
        // given
        LocalDateTime rentalStartDate = LocalDateTime.of(2021, 1, 10, 11, 00, 00);
        BookRental bookRental = BookRental.builder()
                .startDate(rentalStartDate)
                .book(new Book())
                .build();

        assertThat(bookRental.isOverDue(rentalStartDate.plusWeeks(1).toLocalDate())).isFalse();
        assertThat(bookRental.isOverDue(rentalStartDate.plusWeeks(2).toLocalDate())).isFalse();
        assertThat(bookRental.isOverDue(rentalStartDate.plusWeeks(3).toLocalDate())).isTrue();
    }

}