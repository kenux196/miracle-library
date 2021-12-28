package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.BookRentInfo;
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

    public List<BookRentInfo> rentBooks(RequestBookRental requestBookRental) {
        Optional<Member> member = memberRepository.findById(requestBookRental.getMemberId());
        if (member.isEmpty()) {
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }

        List<BookRentInfo> bookRentInfos = new ArrayList<>();
        requestBookRental.getBookIds().forEach(id -> {
            Optional<Book> book = bookRepository.findById(id);
            if (book.isEmpty()) {
                throw new CustomException(ErrorCode.BOOK_NOT_FOUND);
            }
            book.get().changeStatus(BookStatus.RENTED);
            bookRepository.save(book.get());

            BookRentInfo bookRentInfo = BookRentInfo.builder()
                    .member(member.get())
                    .book(book.get())
                    .startDate(LocalDateTime.now())
                    .build();
            BookRentInfo save = bookRentalRepository.save(bookRentInfo);
            bookRentInfos.add(save);
        });

        return bookRentInfos;
    }

    public BookRentInfo returnBook(RequestBookReturn requestBookReturn) {
        final Optional<Book> book = bookRepository.findById(requestBookReturn.getBookId());
        if (book.isEmpty()) {
            throw new CustomException(ErrorCode.BOOK_NOT_FOUND);
        }

        List<BookRentInfo> bookRentInfoList = bookRentalRepository.findAllByBookId(book.get().getId());

        List<BookRentInfo> found = bookRentInfoList.stream()
                .filter(bookRental -> bookRental.getReturnDate() == null)
                .collect(Collectors.toList());

        if (found.isEmpty()) {
            throw new CustomException(ErrorCode.RENTAL_INFO_NOT_FOUND);
        }

        if (found.size() > 1) {
            throw new CustomException(ErrorCode.RENTAL_INFO_DUPLICATION);
        }

        BookRentInfo bookRentInfo = found.get(0);
        bookRentInfo.setReturnDate(LocalDateTime.now());
        book.get().changeStatus(BookStatus.AVAILABLE);
        return bookRentalRepository.save(bookRentInfo);
    }
}
