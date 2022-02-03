package org.kenux.miraclelibrary.domain.member.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.dto.LoginRequest;
import org.kenux.miraclelibrary.domain.member.repository.MemberRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.kenux.miraclelibrary.global.exception.ErrorCode;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    LoginService loginService;

    @Test
    @DisplayName("이메일에 해당하는 회원 없으면, 로그인 실패")
    void test_errorLogin_whenNotExistMember() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("user@test.com", "password");
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> loginService.login(loginRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("패스워드 틀리면, 로그인 실패")
    void test_errorLogin_whenWrongPassword() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("user@test.com", "password");
        Member member = getMember("wrongPassword");
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));

        // when then
        assertThatThrownBy(() -> loginService.login(loginRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PASSWORD_WRONG.getMessage());
    }

    @Test
    @DisplayName("정상 입력에 대해서 로그인 성공")
    void test_successLogin() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("user@test.com", "password");
        Member member = getMember("password");
        given(memberRepository.findByEmail(any())).willReturn(Optional.ofNullable(member));
        
        // when
        final Member result = loginService.login(loginRequest);

        // then
        assertThat(result).isNotNull();
    }

    private Member getMember(String password) {
        return Member.createCustomer(
                "member1", "member1@test.com", "010-1234-5678", password);
    }
}