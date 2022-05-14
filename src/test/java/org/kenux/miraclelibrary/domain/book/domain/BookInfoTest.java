package org.kenux.miraclelibrary.domain.book.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookInfoTest {

    @Test
    @DisplayName("BookInfo 생성 : 제목, isbn, 저자, 출판일은 필수로 있어야 한다.")
    void createBookInfo() {
        BookInfo book = BookInfo.builder()
                .title("제목")
                .isbn("isbn")
                .author("저자")
                .publishDate(LocalDate.now())
                .build();

        assertThat(book.getTitle()).isNotNull();
        assertThat(book.getIsbn()).isNotNull();
        assertThat(book.getAuthor()).isNotNull();
        assertThat(book.getPublishDate()).isNotNull();
    }

    @Test
    @DisplayName("BookInfo 내용 변경")
    void updateBookInfo() {
        BookInfo book = BookInfo.builder()
                .title("제목")
                .isbn("isbn")
                .author("저자")
                .publishDate(LocalDate.now())
                .build();

        BookInfo update = BookInfo.builder()
                .title("제목1")
                .isbn("isbn1")
                .author("저자1")
                .publishDate(LocalDate.now().plusDays(1))
                .subTitle("부제목1")
                .build();

        book.update(update);

        assertThat(book.getTitle()).isEqualTo(update.getTitle());
        assertThat(book.getAuthor()).isEqualTo(update.getAuthor());
        assertThat(book.getIsbn()).isEqualTo(update.getIsbn());
        assertThat(book.getPublishDate()).isEqualTo(update.getPublishDate());
        assertThat(book.getSubTitle()).isEqualTo(update.getSubTitle());
    }

    @Test
    @DisplayName("도서 정보는 실제 책을 가진다.")
    void hasBookInfo() {
        BookInfo bookInfo = BookInfo.builder()
                .title("제목")
                .isbn("isbn")
                .author("저자")
                .publishDate(LocalDate.now())
                .build();
        Book book = Book.createNewBook();
        bookInfo.addBook(book);

        assertThat(book.getBookInfo()).isEqualTo(bookInfo);
        assertThat(bookInfo.getBooks()).isNotEmpty();
    }
}