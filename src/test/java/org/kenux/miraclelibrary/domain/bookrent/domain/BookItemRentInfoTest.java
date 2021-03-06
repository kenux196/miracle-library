package org.kenux.miraclelibrary.domain.bookrent.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.book.domain.BookItem;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.kenux.miraclelibrary.domain.member.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BookItemRentInfoTest {

    @Test
    @DisplayName("도서 대출 정보가 생성되면 책, 회원, 대출시작일, 대출 종료일을 가져야 한다.")
    void test_create_bookRentInfo() throws Exception {
        Member member = Member.builder().build();
        BookRentInfo bookRentInfo = BookRentInfo.builder()
                .bookItem(createBook())
                .member(member)
                .startDate(LocalDateTime.now())
                .build();

        assertThat(bookRentInfo.getBookItem()).isNotNull();
        assertThat(bookRentInfo.getMember()).isNotNull();
        assertThat(bookRentInfo.getStartDate()).isNotNull();
    }

    @Test
    @DisplayName("대출 시작 날짜를 기준으로 반납일이 계산되어야 한다.")
    void test_calculate_rentEndDate() throws Exception {
        // given
        LocalDateTime startDate = LocalDateTime.of(2021, 1, 1, 13, 0, 0);
        BookRentInfo bookRentInfo = BookRentInfo.builder()
                .bookItem(createBook())
                .startDate(startDate)
                .build();

        // when
        LocalDate returnDate = bookRentInfo.getEndDate();

        // then
        assertThat(returnDate).isEqualTo(startDate.plusWeeks(2).toLocalDate());
    }

    @Test
    @DisplayName("도서 대출 정보에는 책이 반납된 날짜가 있어야 한다.")
    void test_setReturnDate() throws Exception {
        // given
        LocalDateTime returnDate = LocalDateTime.of(2021, 1, 10, 11, 0, 0);
        BookRentInfo bookRentInfo = BookRentInfo.builder()
                .startDate(LocalDateTime.now())
                .bookItem(createBook())
                .build();

        // when
        bookRentInfo.setReturnDate(returnDate);

        // then
        assertThat(bookRentInfo.getReturnDate()).isEqualTo(returnDate);
    }

    @Test
    @DisplayName("도서 대출 정보를 통해 연체 상태를 알 수 있다.")
    void test_isOverDue() {
        // given
        LocalDateTime rentalStartDate = LocalDateTime.of(2021, 1, 10, 11, 0, 0);
        BookRentInfo bookRentInfo = BookRentInfo.builder()
                .startDate(rentalStartDate)
                .bookItem(createBook())
                .build();

        assertThat(bookRentInfo.isOverDue(rentalStartDate.plusWeeks(1).toLocalDate())).isFalse();
        assertThat(bookRentInfo.isOverDue(rentalStartDate.plusWeeks(2).toLocalDate())).isFalse();
        assertThat(bookRentInfo.isOverDue(rentalStartDate.plusWeeks(3).toLocalDate())).isTrue();
    }

    @Test
    @DisplayName("대출된 도서는 상태가 변경된다.")
    void test_() throws Exception {
        // given
        Member member = Member.builder()
                .name("userA")
                .build();
        BookItem bookItem = BookItem.createNewBook();

        // when
        BookRentInfo rentInfo = BookRentInfo.rent(member, bookItem);

        // then
        assertThat(rentInfo.getBookItem().getStatus()).isEqualTo(BookStatus.RENTED);
    }


    private BookItem createBook() {
        return BookItem.createNewBook();
    }
}