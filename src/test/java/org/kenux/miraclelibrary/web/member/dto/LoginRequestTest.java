package org.kenux.miraclelibrary.web.member.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.web.member.dto.request.LoginRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LoginRequestTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
    
    @Test
    @DisplayName("로그인 dto 검증: 실패. 올바른 이메일 주소 아닌 경우")
    void validation_이메일형식오류() {
        LoginRequest loginRequest = new LoginRequest("aaaaa", "password");

        final Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("로그인 dto 검증: 정상")
    void validation_정상인경우() {
        LoginRequest loginRequest = new LoginRequest("test@test.com", "password");

        final Set<ConstraintViolation<LoginRequest>> violations = validator.validate(loginRequest);
        assertThat(violations).isEmpty();
    }

}