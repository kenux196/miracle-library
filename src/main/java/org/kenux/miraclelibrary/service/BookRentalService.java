package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.BookRental;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.repository.BookRentalRepository;
import org.kenux.miraclelibrary.repository.BookRepository;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.RequestBookRental;
import org.kenux.miraclelibrary.rest.dto.RequestBookReturn;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
                .rentalStartDate(LocalDateTime.now())
                .build();
        return bookRentalRepository.save(bookRental);
    }

    public BookRental returnBook(RequestBookReturn requestBookReturn) {
        List<BookRental> bookRentalList =
                bookRentalRepository.findAllByBookId(requestBookReturn.getBookId());

        List<BookRental> found = bookRentalList.stream()
                .filter(bookRental -> bookRental.getReturnDate() == null)
                .collect(Collectors.toList());

        if (found.isEmpty()) {
            throw new RuntimeException("도서 대출 정보 없음");
        }

        if (found.size() > 1) {
            throw new RuntimeException("동일한 대출 정보가 있음.");
        }

        return found.get(0);
    }
}
