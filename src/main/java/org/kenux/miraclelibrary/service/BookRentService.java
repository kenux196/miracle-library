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
import org.kenux.miraclelibrary.rest.dto.BookRentRequestDto;
import org.kenux.miraclelibrary.rest.dto.BookReturnRequestDto;
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
    public List<BookRentInfo> rentBooks(BookRentRequestDto bookRentRequestDto) {
        Member member = getMember(bookRentRequestDto.getMemberId());

        List<BookRentInfo> bookRentInfos = new ArrayList<>();
        bookRentRequestDto.getBookIds().forEach(id -> {
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
    public void returnBook(BookReturnRequestDto bookReturnRequestDto) {
        List<BookRentInfo> bookRentInfoList = getBookRentInfos(bookReturnRequestDto);

        for (BookRentInfo bookRentInfo : bookRentInfoList) {
            bookRentInfo.setReturnDate(LocalDateTime.now());
            bookRentInfo.getBook().changeStatus(BookStatus.RENTABLE);
            bookRentInfoRepository.save(bookRentInfo);
        }
    }

    private List<BookRentInfo> getBookRentInfos(BookReturnRequestDto bookReturnRequestDto) {
        List<BookRentInfo> bookRentInfoList =
                bookRentInfoRepository.findAllByBookIds(bookReturnRequestDto.getBooks()).stream()
                        .filter(bookRental -> bookRental.getReturnDate() == null)
                        .collect(Collectors.toList());

        if (bookRentInfoList.isEmpty()) {
            throw new CustomException(ErrorCode.RENT_INFO_NOT_FOUND);
        }

        if (bookRentInfoList.size() > bookReturnRequestDto.getBooks().size()) {
            throw new CustomException(ErrorCode.RENT_INFO_DUPLICATION);
        }
        return bookRentInfoList;
    }

    public List<BookRentInfo> getRentInfoByMember(Long memberId) {
        final Member member = getMember(memberId);
        return bookRentInfoRepository.findAllByMemberId(member.getId());
    }
}
