package org.kenux.miraclelibrary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.enums.BookStatus;
import org.kenux.miraclelibrary.repository.BookRepository;
import org.kenux.miraclelibrary.rest.dto.RegisterBookDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookService bookService;

    @Test
    @DisplayName("새로운 책을 등록한다.")
    void test_register_newBook() throws Exception {
        // given
        Book book = createBookForTest();
        given(bookRepository.save(any())).willReturn(book);

        // when
        RegisterBookDto registerBookDto = RegisterBookDto.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .build();
        Long result = bookService.registerNewBook(registerBookDto);

        // then
        assertThat(result).isEqualTo(1L);
    }

    @Test
    @DisplayName("제목 혹은 저자에 해당하는 키워드를 통해 책을 검색한다.")
    void test_searchBookByTitle() throws Exception {
        // given
        Book book = createBookForTest();
        given(bookRepository.findAllByKeyword(any())).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.searchBook("title");

        // then
        assertThat(books).isNotEmpty();
    }

    @Test
    @DisplayName("검색한 책의 대출 가능 여부를 확인할 수 있어야 한다.")
    void test_isAvailableRent() throws Exception {
        // given
        Book book = createBookForTest();
        book.changeStatus(BookStatus.RENTED);
        given(bookRepository.findAllByKeyword(any())).willReturn(Collections.singletonList(book));

        // when
        List<Book> books = bookService.searchBook("title");

        // then
        assertThat(books.get(0).getStatus()).isEqualTo(BookStatus.RENTED);
    }

    private Book createBookForTest() {
        return Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .status(BookStatus.RENTABLE)
                .createDate(LocalDateTime.of(2021, 1, 1, 13, 1, 1))
                .build();
    }

}