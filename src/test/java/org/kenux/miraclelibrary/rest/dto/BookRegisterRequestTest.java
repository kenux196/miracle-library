package org.kenux.miraclelibrary.rest.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.enums.BookStatus;

import static org.assertj.core.api.Assertions.assertThat;

class BookRegisterRequestTest {

    @Test
    @DisplayName("새책 등록은 엔티티로 변환할 수 있다.")
    void test_createRegisterBookDto() {
        BookRegisterRequest dto = BookRegisterRequest.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .build();

        assertThat(dto.getTitle()).isNotNull();
        assertThat(dto.getAuthor()).isNotNull();
        assertThat(dto.getIsbn()).isNotNull();
    }

    @Test
    @DisplayName("Book Entity 변환가능하다. 이때, 생성 시간이 있어야 하고, Rentable 상태이어야 한다.")
    void test_toEntity() throws Exception {
        // given
        BookRegisterRequest dto = BookRegisterRequest.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .build();

        // when
        final Book book = dto.toEntity();

        // then
        assertThat(book).isNotNull();
        assertThat(book.getStatus()).isEqualTo(BookStatus.RENTABLE);
        assertThat(book.getCreateDate()).isNotNull();
    }

}