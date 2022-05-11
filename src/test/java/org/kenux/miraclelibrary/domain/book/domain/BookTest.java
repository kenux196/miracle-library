package org.kenux.miraclelibrary.domain.book.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    @DisplayName("BookInfo 생성")
    void create() {

        BookInfo bookInfo = createBook();
        assertThat(bookInfo).isNotNull()
                        .isInstanceOf(BookInfo.class);
    }

    @Test
    @DisplayName("책 필수 정보: 제목, 작가, ISBN, 출판일은 필수")
    void bookBasicInfo() throws Exception {
        BookInfo book = createBook();

        assertThat(book.getTitle()).isEqualTo("제목");
        assertThat(book.getAuthor()).isEqualTo("저자");
        assertThat(book.getIsbn()).isEqualTo("isbn");
        assertThat(book.getPublishDate())
                .isEqualTo(LocalDate.of(2022, 1,1));
    }

    @Test
    @DisplayName("책 정보 변경")
    void updateBookInfo() {
        BookInfo bookInfo = createBook();

        BookInfo newInfo = BookInfo.builder()
                .title("new title")
                .build();

        bookInfo.update(newInfo);

        assertThat(bookInfo.getTitle()).isEqualTo(newInfo.getTitle());
    }


    @Test
    @DisplayName("책 필수정보 추가: 카테고리")
    void hasCategory() {
        BookInfo bookInfo = createBook();
        assertThat(bookInfo.getCategory()).isEqualTo(BookCategory.FICTION);
    }

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
        Book book1 = Book.createBook();
        Book book2 = Book.createBook();

        // when
        book1.changeBookStatus(BookStatus.RENTED);
        book2.changeBookStatus(BookStatus.REMOVED);

        // then
        assertThat(book1.isHeldBook()).isTrue();
        assertThat(book2.isHeldBook()).isFalse();
    }

    private BookInfo createBook() {
        return BookInfo.builder()
                .title("제목")
                .author("저자")
                .isbn("isbn")
                .subTitle("부제목")
                .publishDate(LocalDate.of(2022, 1,1))
                .category(BookCategory.FICTION)
                .summary("요약")
                .cover("표지")
                .build();
    }
}