package org.kenux.miraclelibrary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.BookRental;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.domain.enums.MemberRole;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.exception.ErrorCode;
import org.kenux.miraclelibrary.repository.BookRentalRepository;
import org.kenux.miraclelibrary.repository.BookRepository;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.RequestBookRental;
import org.kenux.miraclelibrary.rest.dto.RequestBookReturn;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookRentalServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookRentalRepository bookRentalRepository;

    @InjectMocks
    BookRentalService bookRentalService;

    @Test
    @DisplayName("멤버는 책을 대여한다.")
    void test_BookRentalService_rentalBook() throws Exception {
        // given
        BookRental bookRental = BookRental.builder()
                .member(getMember())
                .books(Collections.singletonList(getBook1()))
                .rentalStartDate(LocalDateTime.of(2021, 12, 25, 13, 00, 00))
                .build();

        when(memberRepository.findById(any())).thenReturn(Optional.of(getMember()));
        when(bookRepository.findById(any())).thenReturn(Optional.of(getBook1()));
        when(bookRentalRepository.save(any())).thenReturn(bookRental);

        // when
        Long memberId = 1L;
        Long bookId = 1L;
        RequestBookRental requestBookRental = new RequestBookRental(memberId, Collections.singletonList(bookId));
        BookRental saved = bookRentalService.rentalBook(requestBookRental);

        // then
        assertThat(saved.getBooks()).isNotEmpty();
        assertThat(saved.getMember().getId()).isEqualTo(memberId);
        assertThat(saved.getRentalStartDate()).isEqualTo(bookRental.getRentalStartDate());
    }

    @Test
    @DisplayName("도서 대출 시, 회원 정보가 조회되지 않으면 예외 발생")
    void test_memberNotFound_whenRentBook() throws Exception {
        // given
        when(memberRepository.findById(any())).thenReturn(Optional.empty());

        // when
        Long memberId = 1L;
        Long bookId = 1L;
        RequestBookRental requestBookRental = new RequestBookRental(memberId, Collections.singletonList(bookId));

        assertThatThrownBy(() -> bookRentalService.rentalBook(requestBookRental))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("도서 대출 시, 책 정보가 조회되지 않으면 예외 발생")
    void test_bookNotFound_whenRentBook() throws Exception {
        // given
        when(bookRepository.findById(any())).thenReturn(Optional.empty());
        when(memberRepository.findById(any())).thenReturn(Optional.of(getMember()));

        // when
        Long memberId = 1L;
        Long bookId = 1L;
        RequestBookRental requestBookRental = new RequestBookRental(memberId, Collections.singletonList(bookId));

        // then
        assertThatThrownBy(() -> bookRentalService.rentalBook(requestBookRental))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.BOOK_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("멤버는 책을 반납한다.")
    void test_bookReturn() throws Exception {
        // given
        Book book = getBook1();
        BookRental bookRental = BookRental.builder()
                .member(getMember())
                .books(Collections.singletonList(book))
                .rentalStartDate(LocalDateTime.of(2021, 1, 1, 13, 00, 00))
                .build();
        given(bookRepository.findById(any())).willReturn(Optional.of(book));
        given(bookRentalRepository.save(any())).willReturn(bookRental);
        given(bookRentalRepository.findAllByBook(any())).willReturn(List.of(bookRental));

        // when
        RequestBookReturn requestBookReturn = new RequestBookReturn(book.getId(), "title");
        BookRental result = bookRentalService.returnBook(requestBookReturn);

        // then
        assertThat(result.getReturnDate()).isNotNull();
    }

    private Member getMember() {
        Member member = new Member("member1", "member1@test.com", "password", MemberRole.CUSTOMER);
        ReflectionTestUtils.setField(member, "id", 1L);
        return member;
    }

    private Book getBook1() {
        Book book = new Book("title", "author", "isbn", LocalDate.of(2020, 12, 20));
        ReflectionTestUtils.setField(book, "id", 1L);
        return book;
    }

}