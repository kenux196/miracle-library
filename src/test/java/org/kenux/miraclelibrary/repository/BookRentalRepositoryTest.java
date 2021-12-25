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
    void test_bookRental() throws Exception {
        // given
        Member member = getMember();
        Book book = getBook();
        LocalDateTime rentalDate = LocalDateTime.of(2021, 1, 1, 13, 00, 00);

        // when
        BookRental bookRental = new BookRental(member, book, rentalDate);
        BookRental save = bookRentalRepository.save(bookRental);

        // then
        assertThat(save.getId()).isNotNull();
        assertThat(save.getBook()).isEqualTo(book);
        assertThat(save.getBook().getId()).isEqualTo(book.getId());
        assertThat(save.getMember().getId()).isEqualTo(member.getId());
        assertThat(save.getRentalStartDate()).isEqualTo(rentalDate);
        assertThat(save.getReturnDate()).isNull();
        assertThat(save.getRentalEndDate()).isNotNull();
    }

    @Test
    @DisplayName("멤버가 책을 반납하면, 정보가 갱신되어야 한다.")
    void test_bookReturn() throws Exception {
        // given
        Member member = getMember();
        Book book = getBook();
        LocalDateTime rentalDate = LocalDateTime.of(2021, 1, 1, 13, 00, 00);
        BookRental bookRental = new BookRental(member, book, rentalDate);
        bookRentalRepository.save(bookRental);

        // when
        LocalDateTime returnDate = LocalDateTime.of(2021, 1, 19, 11, 00, 00);
        bookRental.setReturnDate(returnDate);
        BookRental save = bookRentalRepository.save(bookRental);

        // then
        assertThat(save.getReturnDate()).isNotNull();
        assertThat(save.getReturnDate()).isEqualTo(returnDate);
    }

    @Test
    @DisplayName("대여 정보는 멤버로 조회할 수 있다.")
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
    @DisplayName("대여 정보는 책으로 검색할 수 있다.")
    void test_findAllByBook() throws Exception {
        // given
        Member member = getMember();
        Book book = getBook();
        LocalDateTime rentalDate = LocalDateTime.of(2021, 1, 1, 13, 00, 00);
        BookRental bookRental = new BookRental(member, book, rentalDate);
        bookRentalRepository.save(bookRental);

        // when
        List<BookRental> bookRentals = bookRentalRepository.findAllByBookId(1L);

        // then
        assertThat(bookRentals).isNotEmpty();
    }

    @Test
    @DisplayName("멤버는 여러 권의 책을 대여할 수 있다.")
    void test_rent_severalBooks() throws Exception {
        // given
        for (int i = 0; i < 3; i++) {
            Book book = Book.builder()
                    .title("title" + i)
                    .author("author" + i)
                    .isbn("isbn" + i)
                    .build();
            bookRepository.save(book);
        }
        List<Book> bookList = bookRepository.findAll();
        Member member = getMember();
        bookList.forEach(book -> {
            LocalDateTime rentalDate = LocalDateTime.of(2021, 1, 1, 13, 00, 00);
            BookRental bookRental = new BookRental(member, book, rentalDate);
            bookRentalRepository.save(bookRental);
        });

        // when
        List<BookRental> rentalBooks = bookRentalRepository.findAllByMemberId(member.getId());

        // then
        assertThat(rentalBooks).hasSize(3);
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