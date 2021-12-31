package org.kenux.miraclelibrary.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.exception.ErrorCode;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.MemberJoinRequest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.kenux.miraclelibrary.exception.ErrorCode.EMAIL_DUPLICATION;
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
    @DisplayName("회원 가입을 한다.")
    void test_joinMember() {
        // given
        when(memberRepository.save(any())).thenReturn(member);

        // when
        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .build();
        Long savedId = memberService.join(memberJoinRequest);

        // then
        assertThat(savedId).isEqualTo(1);
    }

    @Test
    @DisplayName("회원 가입시 이메일 중복인 경우, exception 발생")
    void test_duplicateEmail() {
        // given
        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .build();
        given(memberRepository.existsByEmail(any())).willReturn(true);

        // when then
        assertThatThrownBy(() -> memberService.join(memberJoinRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(EMAIL_DUPLICATION.getMessage());
    }

    @Test
    @DisplayName("회원 패스워드 검증 - 길이가 8자보다 작으면 예외 발생")
    void test_validatePassword() {
        // given
        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("pass")
                .build();

        // when then
        assertThatThrownBy(() -> memberService.join(memberJoinRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PASSWORD_SHORT.getMessage());
    }

    @Test
    @DisplayName("가입된 회원 전체를 조회할 수 있어야 한다.")
    void test_findAllCustomer() {
        // given
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String name = "customer" + 1;
            String email = name +"@email.com";
            String password = "password";
            members.add(Member.builder()
                    .name(name)
                    .email(email)
                    .password(password)
                    .build());
        }

        when(memberRepository.findAll()).thenReturn(members);

        // when
        List<Member> memberList = memberService.getAllCustomer();

        // then
        assertThat(memberList).hasSize(100);
    }

    @Test
    @DisplayName("회원 이름으로 고객 조회가 가능해야 한다.")
    void test_findByCustomerName() {
        // given
        when(memberRepository.findByName(any())).thenReturn(Optional.of(member));

        // when
        Optional<Member> result = memberService.getCustomerByName("customer1");

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo("customer1");
    }

    @Test
    @DisplayName("회원 아이디를 통한 회원 조회가 가능하다.")
    void test_findByCustomerId() {
        // given
        when(memberRepository.findById(any())).thenReturn(Optional.of(member));

        // when
        Optional<Member> result = memberService.getCustomer(1L);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(1L);
    }

    private Member getMember() {
        Member member = Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .build();
        ReflectionTestUtils.setField(member, "id", 1L);
        return member;
    }


}