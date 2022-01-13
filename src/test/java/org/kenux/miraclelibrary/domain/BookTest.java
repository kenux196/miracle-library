package org.kenux.miraclelibrary.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.enums.BookStatus;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    @DisplayName("책은 제목, 저자, isbn, 등록일자를 가진다.")
    void test_create_book() {
        Book book = createBook();

        assertThat(book.getTitle()).isEqualTo("제목");
        assertThat(book.getAuthor()).isEqualTo("저자");
        assertThat(book.getIsbn()).isEqualTo("isbn");
        assertThat(book.getCreateDate()).isEqualTo(createDate());
    }

    @Test
    @DisplayName("책은 대출 가능 여부에 대한 상태를 변경할 수 있다.")
    void test_changeStatus() {
        Book book = createBook();

        book.changeStatus(BookStatus.RENTABLE);

        assertThat(book.getStatus()).isEqualTo(BookStatus.RENTABLE);
    }

    @Test
    @DisplayName("등록일이 없으면, 등록일 추가 가능")
    void test_setCreateDate() throws Exception {
        // given
        final Book book = Book.builder()
                .title("제목")
                .author("저자")
                .isbn("isbn")
                .build();

        final LocalDateTime createDate = LocalDateTime.of(2022, 1, 1, 1, 1);

        // when
        book.setCreateDate(createDate);

        // then
        assertThat(book.getCreateDate()).isEqualTo(createDate);
    }

    @Test
    @DisplayName("등록일이 있으면, 등록일이 변경되면 안된다.")
    void test_setCreateDateSkip() throws Exception {
        // given
        final LocalDateTime createDate = LocalDateTime.of(2022, 1, 1, 1, 1);
        final Book book = Book.builder()
                .title("제목")
                .author("저자")
                .isbn("isbn")
                .createDate(createDate)
                .build();

        // when
        book.setCreateDate(LocalDateTime.now());

        // then
        assertThat(book.getCreateDate()).isEqualTo(createDate);
    }


    private Book createBook() {
        return Book.builder()
                .title("제목")
                .author("저자")
                .isbn("isbn")
                .createDate(createDate())
                .build();
    }

    private LocalDateTime createDate() {
        return LocalDateTime.of(2021, 1, 1, 1, 1, 1);
    }
}