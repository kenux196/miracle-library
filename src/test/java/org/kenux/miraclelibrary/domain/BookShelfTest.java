package org.kenux.miraclelibrary.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

class BookShelfTest {

    @Test
    @DisplayName("회원이 대여한 책은 책장에 저장된다.")
    void test_bookShelf_addBook() {
        // given
        BookShelf bookShelf = new BookShelf();
        Book book = Book.builder().build();

        // when
        bookShelf.addBook(book);

        // then
        assertThat(bookShelf.getBookList()).hasSize(1);
    }

    @Test
    @DisplayName("회원이 반납한 책은 책장에서 삭제된다.")
    void test_bookShelf_removeBook() {
        // given
        BookShelf bookShelf = new BookShelf();
        Book book = new Book();
        bookShelf.addBook(book);

        // when
        bookShelf.removeBook(book);

        // then
        assertThat(bookShelf.getBookList()).isEmpty();
    }

    @Test
    @DisplayName("책장에서 제목으로 책을 검색할 수 있다.")
    void test_bookShelf_findBookByBook() {
        // given
        BookShelf bookShelf = new BookShelf();
        for (int i = 0; i < 10; i++) {
            bookShelf.addBook(
                    Book.builder()
                            .title("book" + i)
                            .build());
        }

        // when
        Book book = Book.builder()
                .title("book4")
                .build();
        List<Book> found = bookShelf.findBook(book);

        // then
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTitle()).isEqualTo("book4");
    }
}