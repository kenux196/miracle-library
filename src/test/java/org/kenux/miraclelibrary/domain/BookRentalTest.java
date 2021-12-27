package org.kenux.miraclelibrary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookRentalTest {

    @Test
    @DisplayName("도서 대출 정보에는 책, 멤버, 대출시작일, 대출번호가 포함된다.")
    void test_bookRental_Basic() throws Exception {
        Member member = Member.builder().build();
        BookRental bookRental = BookRental.builder()
                .books(Collections.singletonList(new Book()))
                .member(member)
                .rentalStartDate(LocalDateTime.now())
                .build();

        assertThat(bookRental.getBooks()).isNotEmpty();
        assertThat(bookRental.getMember()).isNotNull();
        assertThat(bookRental.getRentalStartDate()).isNotNull();
    }

    @Test
    @DisplayName("도서 대출 정보에는 책은 여러 권이 포함될 수 있다.")
    void test_bookRental_hasBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Book book = Book.builder()
                    .title("title" + i)
                    .author("author" + i)
                    .isbn("isbn" + i)
                    .build();
            ReflectionTestUtils.setField(book, "id", (long) i);
            books.add(book);
        }
        Member member = Member.builder().build();

        BookRental bookRental = BookRental.builder()
                .books(books)
                .member(member)
                .rentalStartDate(LocalDateTime.now())
                .build();

        assertThat(bookRental.getBooks()).isNotEmpty();
        assertThat(bookRental.getMember()).isNotNull();
        assertThat(bookRental.getRentalStartDate()).isNotNull();
    }

    @Test
    @DisplayName("도서 대출 정보를 통해서 반납 예정일을 알 수 있어야 한다.")
    void test_bookRental_getRentalEndDate() throws Exception {
        // given
        LocalDateTime startDate = LocalDateTime.of(2021, 1, 1, 13, 00, 00);
        BookRental bookRental = BookRental.builder()
                .books(Collections.singletonList(new Book()))
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
                .books(Collections.singletonList(new Book()))
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
                .rentalStartDate(rentalStartDate)
                .books(Collections.singletonList(new Book()))
                .build();

        assertThat(bookRental.isOverDue(rentalStartDate.plusWeeks(1).toLocalDate())).isFalse();
        assertThat(bookRental.isOverDue(rentalStartDate.plusWeeks(2).toLocalDate())).isFalse();
        assertThat(bookRental.isOverDue(rentalStartDate.plusWeeks(3).toLocalDate())).isTrue();
    }

}