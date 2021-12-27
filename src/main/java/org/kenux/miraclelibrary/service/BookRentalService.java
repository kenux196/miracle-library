package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.BookRental;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.domain.enums.BookStatus;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.exception.ErrorCode;
import org.kenux.miraclelibrary.repository.BookRentalRepository;
import org.kenux.miraclelibrary.repository.BookRepository;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.RequestBookRental;
import org.kenux.miraclelibrary.rest.dto.RequestBookReturn;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookRentalService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BookRentalRepository bookRentalRepository;

    public BookRental rentBooks(RequestBookRental requestBookRental) {
        Optional<Member> member = memberRepository.findById(requestBookRental.getMemberId());
        if (member.isEmpty()) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        List<Book> bookList = new ArrayList<>();
        requestBookRental.getBookIds().forEach(id -> {
            Optional<Book> book = bookRepository.findById(id);
            if (book.isEmpty()) {
                throw new CustomException(ErrorCode.BOOK_NOT_FOUND);
            }
            book.get().changeStatus(BookStatus.RENTED);
            bookList.add(book.get());
        });

        BookRental bookRental = BookRental.builder()
                .member(member.get())
                .books(bookList)
                .rentalStartDate(LocalDateTime.now())
                .build();
        return bookRentalRepository.save(bookRental);
    }

    public BookRental returnBook(RequestBookReturn requestBookReturn) {
        final Optional<Book> book = bookRepository.findById(requestBookReturn.getBookId());
        if (book.isEmpty()) {
            throw new CustomException(ErrorCode.BOOK_NOT_FOUND);
        }

        List<BookRental> bookRentalList = bookRentalRepository.findAllByBook(book.get().getId());

        List<BookRental> found = bookRentalList.stream()
                .filter(bookRental -> bookRental.getReturnDate() == null)
                .collect(Collectors.toList());

        if (found.isEmpty()) {
            throw new CustomException(ErrorCode.RENTAL_INFO_NOT_FOUND);
        }

        if (found.size() > 1) {
            throw new CustomException(ErrorCode.RENTAL_INFO_DUPLICATION);
        }

        BookRental bookRental = found.get(0);
        bookRental.setReturnDate(LocalDateTime.now());
        return bookRentalRepository.save(bookRental);
    }
}
