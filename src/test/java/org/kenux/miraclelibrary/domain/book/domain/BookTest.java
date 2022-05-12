package org.kenux.miraclelibrary.domain.book.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    @DisplayName("상태 변경 : 보유, 대여중, 유실, 파기")
    void changeStatus() {
        Book book = new Book();
        book.changeBookStatus(BookStatus.RENTABLE);
        assertThat(book.getStatus()).isEqualTo(BookStatus.RENTABLE);

        book.changeBookStatus(BookStatus.RENTED);
        assertThat(book.getStatus()).isEqualTo(BookStatus.RENTED);

        book.changeBookStatus(BookStatus.REMOVED);
        assertThat(book.getStatus()).isEqualTo(BookStatus.REMOVED);

        book.changeBookStatus(BookStatus.LOST);
        assertThat(book.getStatus()).isEqualTo(BookStatus.LOST);
    }

    @Test
    @DisplayName("보유책인지 확인")
    void heldBook() throws Exception {
        // given
        BookInfo bookInfo = BookInfo.builder()
                .title("제목")
                .isbn("isbn")
                .author("저자")
                .publishDate(LocalDate.now())
                .build();
        Book book1 = Book.createBook(bookInfo);
        Book book2 = Book.createBook(bookInfo);

        // when
        book1.changeBookStatus(BookStatus.RENTED);
        book2.changeBookStatus(BookStatus.REMOVED);

        // then
        assertThat(book1.isHeldBook()).isTrue();
        assertThat(book2.isHeldBook()).isFalse();
    }

    @Test
    @DisplayName("Book은 BookInfo 를 가진다")
    void hasBookInfo() {
        BookInfo bookInfo = BookInfo.builder()
                .title("제목")
                .isbn("isbn")
                .author("저자")
                .publishDate(LocalDate.now())
                .build();

        Book book = Book.createBook(bookInfo);
        assertThat(book.getBookInfo()).isEqualTo(bookInfo);
    }
}