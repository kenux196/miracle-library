package org.kenux.miraclelibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.BookRentInfo;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.domain.enums.BookStatus;
import org.kenux.miraclelibrary.domain.enums.MemberRole;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.exception.ErrorCode;
import org.kenux.miraclelibrary.repository.BookRentInfoRepository;
import org.kenux.miraclelibrary.repository.BookRepository;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.RequestRentBookDto;
import org.kenux.miraclelibrary.rest.dto.RequestReturnBookDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookRentInfoServiceTest {

    @Mock
    MemberRepository memberRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    BookRentInfoRepository bookRentInfoRepository;

    @InjectMocks
    BookRentInfoService bookRentInfoService;

    private Member member;
    private Book book;

    @BeforeEach
    void setup() {
        member = getMember();
        book = getBook();
    }

    @Test
    @DisplayName("멤버의 대여 정보 이력 조회한다.")
    void test_searchRentInfoByMember() throws Exception {
        // given
        Long memberId = 1L;
        BookRentInfo bookRentInfo = BookRentInfo.builder()
                .member(member)
                .book(book)
                .startDate(LocalDateTime.of(2021, 12, 25, 13, 0, 0))
                .build();

        given(memberRepository.findById(any())).willReturn(Optional.of(member));
        given(bookRentInfoRepository.findAllByMemberId(memberId)).willReturn(List.of(bookRentInfo));

        // when
        List<BookRentInfo> bookRentInfos = bookRentInfoService.getRentInfoByMember(memberId);

        // then
        assertThat(bookRentInfos).hasSize(1);
        verify(memberRepository).findById(memberId);
    }


    @Test
    @DisplayName("멤버는 책을 대여한다.")
    void test_BookRentalService_rentalBook() throws Exception {
        // given
        BookRentInfo bookRentInfo = BookRentInfo.builder()
                .member(member)
                .book(book)
                .startDate(LocalDateTime.of(2021, 12, 25, 13, 0, 0))
                .build();
        ReflectionTestUtils.setField(bookRentInfo, "id", 1L);

        given(memberRepository.findById(any())).willReturn(Optional.of(member));
        given(bookRepository.findById(any())).willReturn(Optional.of(book));
        given(bookRentInfoRepository.save(any())).willReturn(bookRentInfo);

        // when
        RequestRentBookDto requestRentBookDto = new RequestRentBookDto(member.getId(), Collections.singletonList(book.getId()));
        List<BookRentInfo> saved = bookRentInfoService.rentBooks(requestRentBookDto);

        // then
        assertThat(saved).isNotEmpty();
    }

    @Test
    @DisplayName("도서 대출 시, 회원 정보가 조회되지 않으면 예외 발생")
    void test_memberNotFound_whenRentBook() throws Exception {
        // given
        when(memberRepository.findById(any())).thenReturn(Optional.empty());

        // when
        RequestRentBookDto requestRentBookDto = new RequestRentBookDto(1L, Collections.singletonList(1L));

        assertThatThrownBy(() -> bookRentInfoService.rentBooks(requestRentBookDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("도서 대출 시, 책 정보가 조회되지 않으면 예외 발생")
    void test_bookNotFound_whenRentBook() throws Exception {
        // given
        when(bookRepository.findById(any())).thenReturn(Optional.empty());
        when(memberRepository.findById(any())).thenReturn(Optional.of(member));

        // when
        RequestRentBookDto requestRentBookDto = new RequestRentBookDto(1L, Collections.singletonList(1L));

        // then
        assertThatThrownBy(() -> bookRentInfoService.rentBooks(requestRentBookDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.BOOK_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("이미 대출된 책은 예외 발생해야 한다.")
    void test_exceptionForRentedBook_whenRentBook() throws Exception {
        // given
        book.changeStatus(BookStatus.RENTED);
        given(memberRepository.findById(any())).willReturn(Optional.of(member));
        given(bookRepository.findById(any())).willReturn(Optional.of(book));

        RequestRentBookDto requestRentBookDto = new RequestRentBookDto(1L, Collections.singletonList(1L));

        // when then
        assertThatThrownBy(() -> bookRentInfoService.rentBooks(requestRentBookDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.BOOK_WAS_RENTED.getMessage());
    }

    @Test
    @DisplayName("책 반납 시, 대여 정보를 못찾은 경우 예외 발생")
    void test_exception_notFoundBookRentInfo() throws Exception {
        // given
        Long memberId = 1L;
        List<Long> books = Collections.singletonList(1L);

        // when
        RequestReturnBookDto requestReturnBookDto = new RequestReturnBookDto(memberId, books);

        // then
        assertThatThrownBy(() -> bookRentInfoService.returnBook(requestReturnBookDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.RENT_INFO_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("멤버는 책을 반납한다.")
    void test_bookReturn() throws Exception {
        // given
        Long memberId = 1L;
        List<Long> books = Collections.singletonList(1L);

        BookRentInfo bookRentInfo = BookRentInfo.builder()
                .member(member)
                .book(book)
                .startDate(LocalDateTime.of(2021, 1, 1, 13, 0, 0))
                .build();
        given(bookRentInfoRepository.save(any())).willReturn(bookRentInfo);
        given(bookRentInfoRepository.findAllByBookIds(any())).willReturn(List.of(bookRentInfo));

        // when
        RequestReturnBookDto requestReturnBookDto = new RequestReturnBookDto(memberId, books);
//        final List<BookRentInfo> results = bookRentInfoService.returnBook(requestReturnBookDto);

        // then
        assertThatNoException().isThrownBy(() -> bookRentInfoService.returnBook(requestReturnBookDto));
//        assertThat(results).hasSize(1);
    }

    @Test
    @DisplayName("멤버는 여러 권의 책을 반납한다.")
    void test_returnSeveralBooks() throws Exception {
        // given
        Long memberId = 1L;
        List<Long> books = Stream.of(1L, 2L, 3L).collect(Collectors.toList());

        List<BookRentInfo> bookRentInfos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Book book = Book.builder()
                    .id(i + 1L)
                    .title("title-" + i)
                    .author("author")
                    .isbn("isbn-" + i)
                    .createDate(LocalDateTime.now())
                    .status(BookStatus.RENTED)
                    .build();
            BookRentInfo bookRentInfo = BookRentInfo.builder()
                    .member(member)
                    .book(book)
                    .startDate(LocalDateTime.now())
                    .build();
            ReflectionTestUtils.setField(bookRentInfo, "id", i + 1L);
            bookRentInfos.add(bookRentInfo);
        }
        given(bookRentInfoRepository.findAllByBookIds(any())).willReturn(bookRentInfos);

        // when
        RequestReturnBookDto requestReturnBookDto = new RequestReturnBookDto(memberId, books);

        // then
        assertThatNoException().isThrownBy(() -> bookRentInfoService.returnBook(requestReturnBookDto));

        // verify
        verify(bookRentInfoRepository).save(bookRentInfos.get(0));
    }

    private Member getMember() {
        Member member = new Member("member1", "member1@test.com", "password", MemberRole.CUSTOMER);
        ReflectionTestUtils.setField(member, "id", 1L);
        return member;
    }

    private Book getBook() {
        return Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .status(BookStatus.RENTABLE)
                .createDate(LocalDateTime.of(2020, 12, 20, 1, 1, 1))
                .build();
    }

}