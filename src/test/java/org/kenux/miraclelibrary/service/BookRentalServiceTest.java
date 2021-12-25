package org.kenux.miraclelibrary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.repository.BookRepository;
import org.kenux.miraclelibrary.rest.dto.RequestBookRental;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.kenux.miraclelibrary.domain.QBook.book;
import static org.kenux.miraclelibrary.domain.QMember.member;

@ExtendWith(MockitoExtension.class)
class BookRentalServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookRentalService bookRentalService;

    @Test
    @DisplayName("멤버는 책을 대여한다.")
    void test_BookRentalService_rentalBook() throws Exception {
        // given
        Long memberId = 1L;
        Long bookId = 1L;
        RequestBookRental requestBookRental = new RequestBookRental(memberId, bookId);


        // when
//        bookRentalService.rentalBook(requestBookRental);

        // then

    }

}