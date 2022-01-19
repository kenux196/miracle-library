package org.kenux.miraclelibrary.domain.bookrent.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.kenux.miraclelibrary.domain.book.repository.BookRepository;
import org.kenux.miraclelibrary.domain.bookrent.repository.BookRentInfoRepository;
import org.kenux.miraclelibrary.domain.bookrent.dto.BookRentRequest;
import org.kenux.miraclelibrary.domain.bookrent.dto.BookReturnRequest;
import org.kenux.miraclelibrary.domain.bookrent.domain.BookRentInfo;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.repository.MemberRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.kenux.miraclelibrary.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookRentService {

    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;
    private final BookRentInfoRepository bookRentInfoRepository;

    @Transactional
    public List<BookRentInfo> rentBooks(BookRentRequest bookRentRequest) {
        Member member = getMember(bookRentRequest.getMemberId());

        List<BookRentInfo> bookRentInfos = new ArrayList<>();
        bookRentRequest.getBookIds().forEach(id -> {
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

    private Member getMember(Long id) {
        return memberRepository.findById(id)
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
    public void returnBook(BookReturnRequest bookReturnRequest) {
        List<BookRentInfo> bookRentInfoList = getBookRentInfos(bookReturnRequest);

        for (BookRentInfo bookRentInfo : bookRentInfoList) {
            bookRentInfo.setReturnDate(LocalDateTime.now());
            bookRentInfo.getBook().changeStatus(BookStatus.RENTABLE);
            bookRentInfoRepository.save(bookRentInfo);
        }
    }

    private List<BookRentInfo> getBookRentInfos(BookReturnRequest bookReturnRequest) {
        List<BookRentInfo> bookRentInfoList =
                bookRentInfoRepository.findAllByBookIds(bookReturnRequest.getBooks()).stream()
                        .filter(bookRental -> bookRental.getReturnDate() == null)
                        .collect(Collectors.toList());

        if (bookRentInfoList.isEmpty()) {
            throw new CustomException(ErrorCode.RENT_INFO_NOT_FOUND);
        }

        if (bookRentInfoList.size() > bookReturnRequest.getBooks().size()) {
            throw new CustomException(ErrorCode.RENT_INFO_DUPLICATION);
        }
        return bookRentInfoList;
    }

    public List<BookRentInfo> getRentInfoByMember(Long memberId) {
        final Member member = getMember(memberId);
        return bookRentInfoRepository.findAllByMemberId(member.getId());
    }
}
