package org.kenux.miraclelibrary.domain.member.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.dto.MemberJoinRequest;
import org.kenux.miraclelibrary.domain.member.dto.MemberJoinRequestBuilder;
import org.kenux.miraclelibrary.domain.member.repository.MemberRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.kenux.miraclelibrary.global.exception.ErrorCode;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.kenux.miraclelibrary.global.exception.ErrorCode.EMAIL_DUPLICATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MemberJoinServiceTest {

    @Mock
    MemberRepository memberRepository;

    MemberJoinService memberJoinService;

    private Member member;

    @BeforeEach
    void setUp() {
        memberJoinService = new MemberJoinServiceImpl(memberRepository);
        member = Member.createCustomer(
                "user", "user@test.com", "010-1234-1234", "password");
        ReflectionTestUtils.setField(member, "id", 1L);
    }

    @Test
    @DisplayName("join: 회원 가입시 이메일 중복인 경우, 이메일중복 예외 발생")
    void join_이메일중복인경우_이메일중복_예외반환() {
        // given
        MemberJoinRequest memberJoinRequest = getMemberJoinRequest("password");
        given(memberRepository.existsByEmail(any())).willReturn(true);

        // when then
        assertThatThrownBy(() -> memberJoinService.join(memberJoinRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(EMAIL_DUPLICATION.getMessage());
    }

    @Test
    @DisplayName("join: 회원 가입시 패스워드 길이가 8자보다 작으면 password short 예외 발생")
    void join_패스워드길이가_8자미만이면_예외반환() {
        // given
        MemberJoinRequest memberJoinRequest = getMemberJoinRequest("passwd");

        // when then
        assertThatThrownBy(() -> memberJoinService.join(memberJoinRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PASSWORD_SHORT.getMessage());
    }

    @Test
    @DisplayName("join: 회원 가입 성공시, 회원 번호 반환")
    void join_회원가입성공시_회원번호반환() {
        // given
        MemberJoinRequest memberJoinRequest = getMemberJoinRequest("password");
        when(memberRepository.save(any())).thenReturn(member);

        // when
        Long savedId = memberJoinService.join(memberJoinRequest);

        // then
        assertThat(savedId).isEqualTo(1);
    }

    private MemberJoinRequest getMemberJoinRequest(String password) {
        return MemberJoinRequestBuilder.build(
                "user", "user@test.com", "010-1234-1234", password);
    }
}