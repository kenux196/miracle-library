package org.kenux.miraclelibrary.domain.book.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    @Test
    @DisplayName("책 생성")
    void test_create_book() {
        Book book = createBook();
        assertThat(book).isNotNull()
                        .isInstanceOf(Book.class);
    }

    @Test
    @DisplayName("책 필수 정보: 제목, 작가, ISBN, 출간일을 꼭 가져야 한다.")
    void bookBasicInfo() throws Exception {
        Book book = createBook();

        assertThat(book.getTitle()).isEqualTo("제목");
        assertThat(book.getAuthor()).isEqualTo("저자");
        assertThat(book.getIsbn()).isEqualTo("isbn");
        assertThat(book.getPublicationDate())
                .isEqualTo(LocalDate.of(2022, 1,1));
    }

    @Test
    @DisplayName("Equals And HashCode 테스트")
    void equal() throws Exception {
        // given
        Book book1 = createBook();
        ReflectionTestUtils.setField(book1, "id", 1L);
        Book book2 = createBook();
        ReflectionTestUtils.setField(book2, "id", 1L);

        // when
        boolean equalsResult = book1.equals(book2);
        boolean hashCodeResult = (book1.hashCode() == book2.hashCode());

        // then
        assertThat(equalsResult).isTrue();
        assertThat(hashCodeResult).isTrue();
    }

    @Test
    @DisplayName("책 필수 정보 추가: 카테고리")
    void bookCategory() {
        Book book = createBook();
        assertThat(book.getCategory()).isEqualTo(BookCategory.FICTION);
    }

    @Test
    @DisplayName("책 카테고리 변경")
    void changeBookCategory() {
        Book book = createBook();
        book.changeCategory(BookCategory.ESSAY);
        assertThat(book.getCategory()).isEqualTo(BookCategory.ESSAY);
    }

    @Test
    @DisplayName("책은 상태 변경 : 보유, 대여중, 유실, 파기")
    void changeStatus() {
        Book book = createBook();
        book.changeStatus(BookStatus.RENTABLE);
        assertThat(book.getStatus()).isEqualTo(BookStatus.RENTABLE);

        book.changeStatus(BookStatus.RENTED);
        assertThat(book.getStatus()).isEqualTo(BookStatus.RENTED);

        book.changeStatus(BookStatus.REMOVED);
        assertThat(book.getStatus()).isEqualTo(BookStatus.REMOVED);

        book.changeStatus(BookStatus.LOST);
        assertThat(book.getStatus()).isEqualTo(BookStatus.LOST);
    }

    @Test
    @DisplayName("책 소개 정보 수정")
    void changeContent() throws Exception {
        // given
        Book book = createBook();
        String content = "책은 소개글이 있다.";

        // when
        book.changeContent(content);

        // then
        assertThat(book.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("책은 표지 이미지를 변경")
    void changeCover() throws Exception {
        // given
        Book book = createBook();
        String cover = "/cover/book_id";

        // when
        book.changeCover(cover);

        // then
        assertThat(book.getCover()).isEqualTo(cover);
    }

    @Test
    @DisplayName("보유책인지 확인")
    void heldBook() throws Exception {
        // given
        Book book1 = createBook();
        Book book2 = createBook();

        // when
        book1.changeStatus(BookStatus.RENTED);
        book2.changeStatus(BookStatus.REMOVED);

        // then
        assertThat(book1.isHeldBook()).isTrue();
        assertThat(book2.isHeldBook()).isFalse();
    }

    private Book createBook() {
        return Book.builder()
                .title("제목")
                .author("저자")
                .isbn("isbn")
                .publicationDate(LocalDate.of(2022, 1,1))
                .category(BookCategory.FICTION)
                .build();
    }
}