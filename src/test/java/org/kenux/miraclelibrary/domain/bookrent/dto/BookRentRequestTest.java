package org.kenux.miraclelibrary.domain.bookrent.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class BookRentRequestTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    @DisplayName("validation: 입력값 누락")
    void validation_에러() throws Exception {
        BookRentRequest bookRentRequest = new BookRentRequest(null, List.of());

        // when
        final Set<ConstraintViolation<BookRentRequest>> violations = validator.validate(bookRentRequest);

        // then
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("validation: 입력값 정상")
    void validation_정상() throws Exception {
        BookRentRequest bookRentRequest = new BookRentRequest(1L, List.of(1L));

        // when
        final Set<ConstraintViolation<BookRentRequest>> violations = validator.validate(bookRentRequest);

        // then
        assertThat(violations).isEmpty();
    }




}