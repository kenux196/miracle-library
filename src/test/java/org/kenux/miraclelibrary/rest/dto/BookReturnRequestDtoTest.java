package org.kenux.miraclelibrary.rest.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class BookReturnRequestDtoTest {

    @Test
    @DisplayName("생성 테스트 : 멤버 정보, 반납할 책 리스트 정보 포함해야 한다.")
    void test_createRequestReturnBookDto() throws Exception {
        // given
        Long memberId = 1L;
        List<Long> returnBookList = Stream.of(1L, 2L, 10L).collect(Collectors.toList());

        // when
        BookReturnRequestDto bookReturnRequestDto = new BookReturnRequestDto(memberId, returnBookList);

        // then
        assertThat(bookReturnRequestDto.getMemberId()).isEqualTo(memberId);
        assertThat(bookReturnRequestDto.getBooks()).hasSize(3);
    }
}