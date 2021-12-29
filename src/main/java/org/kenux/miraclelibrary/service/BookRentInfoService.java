package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.BookRentInfo;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.domain.enums.BookStatus;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.exception.ErrorCode;
import org.kenux.miraclelibrary.repository.BookRentInfoRepository;
import org.kenux.miraclelibrary.repository.BookRepository;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.RequestRentBookDto;
import org.kenux.miraclelibrary.rest.dto.RequestReturnBookDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookRentInfoService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BookRentInfoRepository bookRentInfoRepository;

    @Transactional
    public List<BookRentInfo> rentBooks(RequestRentBookDto requestRentBookDto) {
        Member member = getMember(requestRentBookDto);

        List<BookRentInfo> bookRentInfos = new ArrayList<>();
        requestRentBookDto.getBookIds().forEach(id -> {
            Book book = getBookNotRented(id);
            book.changeStatus(BookStatus.RENTED);
            bookRepository.save(book);

            BookRentInfo bookRentInfo = BookRentInfo.builder()
                    .member(member)
                    .book(book)
                    .startDate(LocalDateTime.now())
                    .build();
            BookRentInfo save = bookRentInfoRepository.save(bookRentInfo);
            bookRentInfos.add(save);
        });

        return bookRentInfos;
    }

    private Member getMember(RequestRentBookDto requestRentBookDto) {
        return memberRepository.findById(requestRentBookDto.getMemberId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Book getBookNotRented(Long id) {
        Book book = getBook(id);
        if (book.getStatus().equals(BookStatus.RENTED)) {
            throw new CustomException(ErrorCode.BOOK_WAS_RENTED);
        }
        return book;
    }

    private Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
    }

    @Transactional
    public BookRentInfo returnBook(RequestReturnBookDto requestReturnBookDto) {
        List<BookRentInfo> bookRentInfoList = bookRentInfoRepository.findAllByBookId(requestReturnBookDto.getBookId());

        List<BookRentInfo> found = bookRentInfoList.stream()
                .filter(bookRental -> bookRental.getReturnDate() == null)
                .collect(Collectors.toList());

        if (found.isEmpty()) {
            throw new CustomException(ErrorCode.RENT_INFO_NOT_FOUND);
        }

        if (found.size() > 1) {
            throw new CustomException(ErrorCode.RENT_INFO_DUPLICATION);
        }

        BookRentInfo bookRentInfo = found.get(0);
        bookRentInfo.setReturnDate(LocalDateTime.now());
        Book book = found.get(0).getBook();
        book.changeStatus(BookStatus.RENTABLE);
        bookRepository.save(book);
        return bookRentInfoRepository.save(bookRentInfo);
    }
}
