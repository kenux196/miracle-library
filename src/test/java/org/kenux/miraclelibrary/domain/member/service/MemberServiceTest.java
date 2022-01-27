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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.kenux.miraclelibrary.global.exception.ErrorCode.EMAIL_DUPLICATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private MemberService memberService;

    private Member member;

    @BeforeEach
    void setup() {
        member = getMember();
    }

    @Test
    @DisplayName("join: 회원 가입시 이메일 중복인 경우, 이메일중복 예외 발생")
    void join_이메일중복인경우_이메일중복_예외반환() {
        // given
        MemberJoinRequest memberJoinRequest =
                MemberJoinRequestBuilder.build(
                        "user", "user@test.com", "010-1234-1234", "password");
        given(memberRepository.existsByEmail(any())).willReturn(true);

        // when then
        assertThatThrownBy(() -> memberService.join(memberJoinRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(EMAIL_DUPLICATION.getMessage());
    }

    @Test
    @DisplayName("join: 회원 가입시 패스워드 길이가 8자보다 작으면 password short 예외 발생")
    void join_패스워드길이가_8자미만이면_예외반환() {
        // given
        MemberJoinRequest memberJoinRequest =
                MemberJoinRequestBuilder.build(
                        "user", "user@test.com", "010-1234-1234", "passwd");

        // when then
        assertThatThrownBy(() -> memberService.join(memberJoinRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PASSWORD_SHORT.getMessage());
    }

    @Test
    @DisplayName("join: 회원 가입 성공시, 회원 번호 반환")
    void join_회원가입성공시_회원번호반환() {
        // given
        MemberJoinRequest memberJoinRequest =
                MemberJoinRequestBuilder.build(
                        "user", "user@test.com", "010-1234-1234", "password");
        when(memberRepository.save(any())).thenReturn(member);

        // when
        Long savedId = memberService.join(memberJoinRequest);

        // then
        assertThat(savedId).isEqualTo(1);
    }

    @Test
    @DisplayName("getMembers: 전체회원 조회")
    void getMembers_전체회원조회() {
        // given
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String name = "customer" + 1;
            String email = name + "@email.com";
            final Member member = Member.createCustomer(name, email, "010-1234-5678", "password");
            members.add(member);
        }
        given(memberRepository.findAll()).willReturn(members);

        // when
        List<Member> memberList = memberService.getMembers();

        // then
        assertThat(memberList).hasSize(100);
    }

    @Test
    @DisplayName("getMembersByName: 회원 이름으로 조회")
    void getMembersByName_회원이름으로조회() {
        // given
        given(memberRepository.findByName(any())).willReturn(Optional.of(member));

        // when
        Optional<Member> result = memberService.getMembersByName("customer1");

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo("customer1");
    }

    @Test
    @DisplayName("getMember: 회원 아이디를 통한 회원 조회")
    void getMember_회원아이디로조회() {
        // given
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when
        Optional<Member> result = memberService.getMember(1L);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    private Member getMember() {
        Member member = Member.createCustomer(
                "customer1", "customer1@test.com", "010-1234-5678", "password");
        ReflectionTestUtils.setField(member, "id", 1L);
        return member;
    }


}