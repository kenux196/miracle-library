package org.kenux.miraclelibrary.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.config.JpaTestConfig;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.BookRentInfo;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.domain.enums.BookStatus;
import org.kenux.miraclelibrary.domain.enums.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaTestConfig.class)
class BookRentInfoRepositoryTest {

    @Autowired
    BookRentInfoRepository bookRentInfoRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BookRepository bookRepository;

    private Member member;
    private Book book;
    private LocalDateTime rentalDate;

    @BeforeEach
    void setup() {
        member = getMember();
        book = getBook();
        rentalDate = LocalDateTime.of(2021, 1, 1, 13, 0, 0);
    }

    @Test
    @DisplayName("책 대여 정보 저장")
    void test_saveBookRental() throws Exception {
        // given
        BookRentInfo bookRentInfo = new BookRentInfo(member, book, rentalDate);

        // when
        BookRentInfo save = bookRentInfoRepository.save(bookRentInfo);

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
        BookRentInfo bookRentInfo = new BookRentInfo(member, book, rentalDate);
        bookRentInfoRepository.save(bookRentInfo);

        // when
        List<BookRentInfo> bookRentInfos = bookRentInfoRepository.findAllByMemberId(member.getId());

        // then
        assertThat(bookRentInfos).isNotEmpty();
        assertThat(bookRentInfos.get(0).getBook()).isNotNull();
        assertThat(bookRentInfos.get(0).getBook().getTitle()).isEqualTo("title");
        assertThat(bookRentInfos.get(0).getMember()).isNotNull();
        assertThat(bookRentInfos.get(0).getMember().getName()).isEqualTo("member1");
    }

    @Test
    @DisplayName("책을 통한 대여 정보 검색")
    void test_findAllByBookId() throws Exception {
        // given
        BookRentInfo bookRentInfo = BookRentInfo.builder()
                .member(member)
                .book(book)
                .startDate(rentalDate)
                .build();
        bookRentInfoRepository.save(bookRentInfo);

        // when
        List<BookRentInfo> bookRentInfos = bookRentInfoRepository.findAllByBookId(book.getId());

        // then
        assertThat(bookRentInfos).isNotEmpty();
    }

    @Test
    @DisplayName("여러 권의 책을 통한 대여 정보 검색")
    void test_findAllByBooks() throws Exception {
        // given
        List<Long> bookList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Book book = bookRepository.save(
                    Book.builder()
                            .title("title-" + i)
                            .author("author")
                            .isbn("isbn-" + i)
                            .publicationDate(LocalDate.of(2022, 1, 1).plusMonths(i))
                            .status(BookStatus.RENTABLE)
                            .build());
            bookList.add(book.getId());
            BookRentInfo bookRentInfo = BookRentInfo.builder()
                    .member(member)
                    .book(book)
                    .startDate(rentalDate)
                    .build();
            bookRentInfoRepository.save(bookRentInfo);
        }

        // when
        List<BookRentInfo> bookRentInfos = bookRentInfoRepository.findAllByBookIds(bookList);

        // then
        assertThat(bookRentInfos).hasSize(3);
        assertThat(bookRentInfos.get(0).getBook().getId()).isEqualTo(bookList.get(0));
        assertThat(bookRentInfos.get(0).getBook().getTitle()).isEqualTo("title-0");
        assertThat(bookRentInfos.get(1).getBook().getId()).isEqualTo(bookList.get(1));
        assertThat(bookRentInfos.get(1).getBook().getTitle()).isEqualTo("title-1");
        assertThat(bookRentInfos.get(2).getBook().getId()).isEqualTo(bookList.get(2));
        assertThat(bookRentInfos.get(2).getBook().getTitle()).isEqualTo("title-2");

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
                .status(BookStatus.RENTABLE)
                .publicationDate(LocalDate.of(2022, 1, 1))
                .build();
        return bookRepository.save(book);
    }
}