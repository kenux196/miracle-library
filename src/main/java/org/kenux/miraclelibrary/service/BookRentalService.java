package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.BookRental;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.repository.BookRentalRepository;
import org.kenux.miraclelibrary.repository.BookRepository;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.RequestBookRental;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookRentalService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BookRentalRepository bookRentalRepository;

    public BookRental rentalBook(RequestBookRental requestBookRental) {
        Optional<Member> member = memberRepository.findById(requestBookRental.getMemberId());
        Optional<Book> book = bookRepository.findById(requestBookRental.getBookId());

        BookRental bookRental = BookRental.builder()
                .member(member.get())
                .book(book.get())
                .rentalStartDate(LocalDate.now())
                .build();
        return bookRentalRepository.save(bookRental);
    }
}
