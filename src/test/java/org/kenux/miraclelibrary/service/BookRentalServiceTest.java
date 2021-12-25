package org.kenux.miraclelibrary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.BookRental;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.domain.enums.MemberRole;
import org.kenux.miraclelibrary.repository.BookRentalRepository;
import org.kenux.miraclelibrary.repository.BookRepository;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.RequestBookRental;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
                .book(getBook1())
                .build();

        when(memberRepository.findById(any())).thenReturn(Optional.of(getMember()));
        when(bookRepository.findById(any())).thenReturn(Optional.of(getBook1()));
        when(bookRentalRepository.save(any())).thenReturn(bookRental);

        // when
        Long memberId = 1L;
        Long bookId = 1L;
        RequestBookRental requestBookRental = new RequestBookRental(memberId, bookId);
        BookRental saved = bookRentalService.rentalBook(requestBookRental);

        // then
        assertThat(saved.getBook().getId()).isEqualTo(bookId);
        assertThat(saved.getMember().getId()).isEqualTo(memberId);

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