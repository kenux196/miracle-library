package org.kenux.miraclelibrary.domain.book.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    @DisplayName("신규 Book 생성 시, 기본 상태는 Rentable")
    void createNewBook() {
        Book newBook = Book.createNewBook();

        assertThat(newBook.getStatus()).isEqualTo(BookStatus.RENTABLE);
    }

    @Test
    @DisplayName("상태 변경 : 보유, 대여중, 유실, 파기")
    void changeStatus() {
        Book book = Book.createNewBook();

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
        Book book1 = Book.createNewBook();
        Book book2 = Book.createNewBook();

        // when
        book1.changeBookStatus(BookStatus.RENTED);
        book2.changeBookStatus(BookStatus.REMOVED);

        // then
        assertThat(book1.isHeldBook()).isTrue();
        assertThat(book2.isHeldBook()).isFalse();
    }

    @Test
    @DisplayName("책은 도서 정보를 가져야 한다.")
    void hasBookInfo() {
        Book book = Book.createNewBook();
        BookInfo bookInfo = BookInfo.builder()
                .title("제목")
                .isbn("isbn")
                .author("저자")
                .publishDate(LocalDate.now())
                .build();

        book.setBookInfo(bookInfo);

        assertThat(book.getBookInfo()).isEqualTo(bookInfo);
        assertThat(bookInfo.getBooks()).isEmpty();
    }
}