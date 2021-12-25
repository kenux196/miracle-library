package org.kenux.miraclelibrary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class BookRentalTest {

    @Test
    @DisplayName("도서 대출 정보에는 책, 멤버, 대출시작일, 대출번호가 포함된다.")
    void test_bookRental_hasBook() throws Exception {
        Book book = Book.builder().build();
        Member member = Member.builder().build();
        BookRental bookRental = BookRental.builder()
                .book(book)
                .member(member)
                .rentalStartDate(LocalDateTime.now())
                .build();

        assertThat(bookRental.getBook()).isNotNull();
        assertThat(bookRental.getMember()).isNotNull();
        assertThat(bookRental.getRentalStartDate()).isNotNull();
        assertThat(bookRental.getRentNumber()).isNotNull();
    }

    @Test
    @DisplayName("도서 대출 정보를 통해서 반납 예정일을 알 수 있어야 한다.")
    void test_bookRental_getRentalEndDate() throws Exception {
        // given
        LocalDateTime startDate = LocalDateTime.of(2021, 1, 1, 13, 00, 00);
        BookRental bookRental = BookRental.builder()
                .book(new Book())
                .rentalStartDate(startDate)
                .build();

        // when
        LocalDate returnDate = bookRental.getRentalEndDate();

        // then
        assertThat(returnDate).isEqualTo(startDate.plusWeeks(2).toLocalDate());
    }

    @Test
    @DisplayName("도서 대출 정보에는 책이 반납된 날짜가 있어야 한다.")
    void test_bookRental_getReturnDate() throws Exception {
        // given
        LocalDateTime returnDate = LocalDateTime.of(2021, 1, 10, 11, 00, 00);
        BookRental bookRental = BookRental.builder()
                .rentalStartDate(LocalDateTime.now())
                .book(new Book())
                .build();

        // when
        bookRental.setReturnDate(returnDate);

        // then
        assertThat(bookRental.getReturnDate()).isEqualTo(returnDate);
    }

    @Test
    @DisplayName("책이 대여가 되면, 대여 번호를 생성한다.")
    void test_rentalNumber() throws Exception {
        // given
        Book book = new Book("title", "author", "isbn", LocalDate.now());
        ReflectionTestUtils.setField(book, "id", 1L);
        LocalDateTime startDate = LocalDateTime.of(2021, 1, 10, 11, 00, 00);
        BookRental bookRental = BookRental.builder()
                .book(book)
                .rentalStartDate(startDate)
                .build();

        // when
        String rentNumber = bookRental.getRentNumber();

        // then
        String expectRentNumber = "R-" + book.getId() + "-" + startDate.toEpochSecond(ZoneOffset.UTC);
        assertThat(rentNumber).isEqualTo(expectRentNumber);

    }

    @Test
    @DisplayName("도서 대출 정보를 통해 연체 상태를 알 수 있다.")
    void test_bookRental_isOverDue() {
        // given
        LocalDateTime rentalStartDate = LocalDateTime.of(2021, 1, 10, 11, 00, 00);
        BookRental bookRental = BookRental.builder()
                .rentalStartDate(rentalStartDate)
                .book(new Book())
                .build();

        assertThat(bookRental.isOverDue(rentalStartDate.plusWeeks(1).toLocalDate())).isFalse();
        assertThat(bookRental.isOverDue(rentalStartDate.plusWeeks(2).toLocalDate())).isFalse();
        assertThat(bookRental.isOverDue(rentalStartDate.plusWeeks(3).toLocalDate())).isTrue();
    }

}