package org.kenux.miraclelibrary.web.bookrent.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.bookrent.dto.BookReturnRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BookItemReturnRequestTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    @DisplayName("validation: 에러")
    void validation_에러() throws Exception {
        // given
        final BookReturnRequest returnRequest = new BookReturnRequest(null, null);

        // when
        final Set<ConstraintViolation<BookReturnRequest>> violations = validator.validate(returnRequest);

        // then
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("validation: 정상")
    void validation_정상() throws Exception {
        // given
        final BookReturnRequest returnRequest = new BookReturnRequest(1L, List.of(1L));

        // when
        final Set<ConstraintViolation<BookReturnRequest>> violations = validator.validate(returnRequest);

        // then
        assertThat(violations).isEmpty();
    }
}