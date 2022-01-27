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

class LibrarianJoinRequestTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void init() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }


    @Test
    void validation_정상인경우() {
        LibrarianJoinRequest request =
                LibrarianAddRequestBuilder.build(
                        "user1", "user1@test.com", "010-1234-1234", "password");

        final Set<ConstraintViolation<LibrarianJoinRequest>> violations = validator.validate(request);
        assertThat(violations).isEmpty();
    }

    @Test
    void validation_비정상인경우() {
        LibrarianJoinRequest request =
                LibrarianAddRequestBuilder.build(null, null, null, null);

        final Set<ConstraintViolation<LibrarianJoinRequest>> violations = validator.validate(request);
        assertThat(violations).isNotEmpty();
        for (ConstraintViolation<LibrarianJoinRequest> violation : violations) {
            System.out.println("violation = " + violation.getPropertyPath());
        }
    }

    @Test
    void toEntity() {
        LibrarianJoinRequest request =
                LibrarianAddRequestBuilder.build(
                        "user1", "user1@test.com", "010-1234-1234", "password");

        final Member member = request.toEntity();

        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo(request.getName());
        assertThat(member.getEmail()).isEqualTo(request.getEmail());
        assertThat(member.getPhone()).isEqualTo(request.getPhone());
        assertThat(member.getMemberPassword()).isEqualTo(request.getPassword());
        assertThat(member.getStatus()).isEqualTo(MemberStatus.NORMAL);
        assertThat(member.getRole()).isEqualTo(MemberRole.LIBRARIAN);
    }
}