package org.kenux.miraclelibrary.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.config.JpaTestConfig;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.BookRental;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.domain.enums.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaTestConfig.class)
class BookRentalRepositoryTest {

    @Autowired
    BookRentalRepository bookRentalRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BookRepository bookRepository;

    @Test
    @DisplayName("책 대여 정보 저장")
    void test_saveBookRental() throws Exception {
        // given
        Member member = getMember();
        Book book = getBook();
        LocalDateTime rentalDate = LocalDateTime.of(2021, 1, 1, 13, 0, 0);
        BookRental bookRental = new BookRental(member, book, rentalDate);

        // when
        BookRental save = bookRentalRepository.save(bookRental);

        // then
        assertThat(save.getId()).isNotNull();
        assertThat(save.getBook()).isNotNull();
        assertThat(save.getBook().getId()).isEqualTo(book.getId());
        assertThat(save.getMember().getId()).isEqualTo(member.getId());
        assertThat(save.getStartDate()).isEqualTo(rentalDate);
        assertThat(save.getReturnDate()).isNull();
        assertThat(save.getEndDate()).isNotNull();
    }

    @Test
    @DisplayName("멤버를 통한 대여 정보 검색")
    void test_findAllByMember() {
        // given
        Member member = getMember();
        Book book = getBook();
        LocalDateTime rentalDate = LocalDateTime.of(2021, 1, 1, 13, 00, 00);
        BookRental bookRental = new BookRental(member, book, rentalDate);
        bookRentalRepository.save(bookRental);

        // when
        List<BookRental> bookRentals = bookRentalRepository.findAllByMemberId(member.getId());

        // then
        assertThat(bookRentals).isNotEmpty();
    }

    @Test
    @DisplayName("책을 통한 대여 정보 검색")
    void test_findAllByBook() throws Exception {
        // given
        Member member = getMember();
        Book book = getBook();
        LocalDateTime rentalDate = LocalDateTime.of(2021, 1, 1, 13, 0, 0);
        BookRental bookRental = BookRental.builder()
                .member(member)
                .book(book)
                .startDate(rentalDate)
                .build();
        bookRentalRepository.save(bookRental);

        // when
        List<BookRental> bookRentals = bookRentalRepository.findAllByBookId(book.getId());

        // then
        assertThat(bookRentals).isNotEmpty();
    }

    private Member getMember() {
        Member member = Member.builder()
                .name("member1")
                .email("member1@test.com")
                .password("password")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        return memberRepository.save(member);
    }

    private Book getBook() {
        Book book = Book.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .build();
        return bookRepository.save(book);
    }
}