package org.kenux.miraclelibrary.domain.member.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.domain.member.domain.MemberStatus;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class LibrarianAddRequestTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }


    @Test
    void validation_정상인경우() {
        LibrarianAddRequest request = LibrarianAddRequest.builder()
                .name("librarian1")
                .email("librarian1@miraclelibrary.com")
                .password("password")
                .phone("010-1234-1234")
                .build();

        final Set<ConstraintViolation<LibrarianAddRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @Test
    void validation_비정상인경우() {
        LibrarianAddRequest request = LibrarianAddRequest.builder().build();

        final Set<ConstraintViolation<LibrarianAddRequest>> violations = validator.validate(request);
        assertThat(violations).isNotEmpty();
        for (ConstraintViolation<LibrarianAddRequest> violation : violations) {
            System.out.println("violation = " + violation.getPropertyPath());
        }
    }

    @Test
    void toEntity() {
        LibrarianAddRequest request = LibrarianAddRequest.builder()
                .name("librarian1")
                .email("librarian1@miraclelibrary.com")
                .password("password")
                .phone("010-1234-1234")
                .build();

        final Member member = request.toEntity();

        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo(request.getName());
        assertThat(member.getEmail()).isEqualTo(request.getEmail());
        assertThat(member.getPhone()).isEqualTo(request.getPhone());
        assertThat(member.getMemberPassword()).isEqualTo(request.getPassword());
        assertThat(member.getStatus()).isEqualTo(MemberStatus.NORMAL);
        assertThat(member.getMemberRole()).isEqualTo(MemberRole.LIBRARIAN);


    }
}