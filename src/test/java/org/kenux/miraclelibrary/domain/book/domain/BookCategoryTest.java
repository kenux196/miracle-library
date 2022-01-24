package org.kenux.miraclelibrary.domain.book.domain;

import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.kenux.miraclelibrary.global.exception.ErrorCode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookCategoryTest {

    @Test
    void getBookCategory_정상케이스() {
        String category = "essay";

        final BookCategory bookCategory = BookCategory.getBookCategory(category) ;

        assertThat(bookCategory).isEqualTo(BookCategory.ESSAY);
    }

    @Test
    void getBookCategory_예외케이스() {
        String category = "blog";

        assertThatThrownBy(() -> BookCategory.getBookCategory(category))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PARAMETER_WRONG.getMessage());
    }

}