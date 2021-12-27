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
import java.util.Collections;
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
    void test_bookRental() throws Exception {
        // given
        Member member = getMember();
        List<Book> books = Collections.singletonList(getBook());
        LocalDateTime rentalDate = LocalDateTime.of(2021, 1, 1, 13, 00, 00);
        BookRental bookRental = new BookRental(member, books, rentalDate);

        // when
        BookRental save = bookRentalRepository.save(bookRental);

        // then
        assertThat(save.getId()).isNotNull();
        assertThat(save.getBooks()).isNotEmpty();
        assertThat(save.getMember().getId()).isEqualTo(member.getId());
        assertThat(save.getRentalStartDate()).isEqualTo(rentalDate);
        assertThat(save.getReturnDate()).isNull();
        assertThat(save.getRentalEndDate()).isNotNull();
    }

    @Test
    @DisplayName("멤버를 통한 대여 정보 검색")
    void test_findAllByMember() {
        // given
        Member member = getMember();
        List<Book> books = Collections.singletonList(getBook());
        LocalDateTime rentalDate = LocalDateTime.of(2021, 1, 1, 13, 00, 00);
        BookRental bookRental = new BookRental(member, books, rentalDate);
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
        bookRepository.save(book);
        List<Book> books = Collections.singletonList(book);
        LocalDateTime rentalDate = LocalDateTime.of(2021, 1, 1, 13, 00, 00);
        BookRental bookRental = BookRental.builder()
                .member(member)
                .rentalStartDate(rentalDate)
                .build();
        bookRental.addBook(book);
        bookRentalRepository.save(bookRental);

        // when
        List<BookRental> bookRentals = bookRentalRepository.findAllByBook(books.get(0).getId());

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