package org.kenux.miraclelibrary.domain.book.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookItemTest {

    @Test
    @DisplayName("신규 Book 생성 시, 기본 상태는 Rentable")
    void createNewBook() {
        BookItem newBookItem = BookItem.createNewBook();

        assertThat(newBookItem.getStatus()).isEqualTo(BookStatus.RENTABLE);
    }

    @Test
    @DisplayName("상태 변경 : 보유, 대여중, 유실, 파기")
    void changeStatus() {
        BookItem bookItem = BookItem.createNewBook();

        bookItem.changeBookStatus(BookStatus.RENTED);
        assertThat(bookItem.getStatus()).isEqualTo(BookStatus.RENTED);

        bookItem.changeBookStatus(BookStatus.REMOVED);
        assertThat(bookItem.getStatus()).isEqualTo(BookStatus.REMOVED);

        bookItem.changeBookStatus(BookStatus.LOST);
        assertThat(bookItem.getStatus()).isEqualTo(BookStatus.LOST);
    }

    @Test
    @DisplayName("보유책인지 확인")
    void heldBook() throws Exception {
        // given
        BookItem bookItem1 = BookItem.createNewBook();
        BookItem bookItem2 = BookItem.createNewBook();

        // when
        bookItem1.changeBookStatus(BookStatus.RENTED);
        bookItem2.changeBookStatus(BookStatus.REMOVED);

        // then
        assertThat(bookItem1.isHeldBook()).isTrue();
        assertThat(bookItem2.isHeldBook()).isFalse();
    }

    @Test
    @DisplayName("책은 도서 정보를 가져야 한다.")
    void hasBookInfo() {
        BookItem bookItem = BookItem.createNewBook();
        Book book = Book.builder()
                .title("제목")
                .isbn("isbn")
                .author("저자")
                .publishDate(LocalDate.now())
                .build();

        bookItem.setBook(book);

        assertThat(bookItem.getBook()).isEqualTo(book);
        assertThat(book.getBookItems()).isEmpty();
    }
}