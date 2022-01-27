package org.kenux.miraclelibrary.domain.member.dto;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

class MemberJoinRequestTest {

    private static ValidatorFactory factory;
    private static Validator validator;

    private MemberJoinRequest memberJoinRequest;

    @BeforeAll
    public static void init() {
        factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    void setUp() {
        memberJoinRequest = MemberJoinRequestBuilder.build(
                "user", "user@test.com", "010-1234-1234", "password");
    }

    @Test
    @DisplayName("Validation: 에러")
    void validation_에러() throws Exception {
        final MemberJoinRequest request = MemberJoinRequestBuilder.build(
                null, null, null, null);

        final Set<ConstraintViolation<MemberJoinRequest>> violations = validator.validate(request);

        assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("회원 가입 요청 검증: 정상")
    void validation_정상() throws Exception {
        final Set<ConstraintViolation<MemberJoinRequest>> violations = validator.validate(memberJoinRequest);
        
        assertThat(violations).isEmpty();
    }

    @Test
    @DisplayName("멤버 조인 요청을 멤버 엔티티로 변환")
    void toEntity() throws Exception {
        // when
        Member member = memberJoinRequest.toEntity();

        // then
        assertThat(member).isNotNull();
        assertThat(member.getName()).isEqualTo(memberJoinRequest.getName());
        assertThat(member.getMemberPassword()).isEqualTo(memberJoinRequest.getPassword());
        assertThat(member.getEmail()).isEqualTo(memberJoinRequest.getEmail());
        assertThat(member.getPhone()).isEqualTo(memberJoinRequest.getPhone());
        assertThat(member.getId()).isNull();
    }

    @Test
    @DisplayName("멤버 엔티티로 변화 시, 멤버의 롤이 지정되어야 한다.")
    void toEntity_MemberRole() throws Exception {
        // when
        Member member = memberJoinRequest.toEntity();

        // then
        assertThat(member.getMemberRole()).isEqualTo(MemberRole.CUSTOMER);
    }

    @Test
    @DisplayName("멤버 엔티티로 변화 시, 멤버 상태 노말 설정")
    void toEntity_hasStatus() throws Exception {
        // when
        Member member = memberJoinRequest.toEntity();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.NORMAL);
    }
}