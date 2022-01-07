package org.kenux.miraclelibrary.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.domain.enums.MemberRole;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.exception.ErrorCode;
import org.kenux.miraclelibrary.rest.dto.LoginRequest;
import org.kenux.miraclelibrary.rest.dto.LoginResponse;
import org.kenux.miraclelibrary.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    LoginService loginService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("로그인 성공")
    void test_successLogin() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("user@test.com", "password");
        Member member = Member.builder()
                .name("user")
                .email("user@test.com")
                .memberRole(MemberRole.CUSTOMER)
                .build();
        ReflectionTestUtils.setField(member, "id", 1L);
        given(loginService.login(any())).willReturn(member);

        LoginResponse loginResponse = LoginResponse.of(member);

        // when
        final RequestBuilder request = MockMvcRequestBuilders.post("/login")
                .content(mapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON);

        final ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(loginResponse)))
                .andDo(print());

    }

    @Test
    @DisplayName("가입된 멤버가 없는 경우, 404 리턴")
    void test_memberNotFound_whenLogin() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("user@test.com", "password");
        given(loginService.login(any())).willThrow(new CustomException(ErrorCode.MEMBER_NOT_FOUND));
        // when
        final RequestBuilder request = MockMvcRequestBuilders.post("/login")
                .content(mapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON);
        final ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(status().is4xxClientError())
                .andExpect(status().is(404));
    }

    @Test
    @DisplayName("로그인 패스워드 틀린 경우, 400 리턴")
    void test_loginFailed_wrongPassword() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("user@test.com", "password");
        given(loginService.login(any())).willThrow(new CustomException(ErrorCode.PASSWORD_WRONG));
        // when
        final RequestBuilder request = MockMvcRequestBuilders.post("/login")
                .content(mapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON);
        final ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(status().is4xxClientError())
                .andExpect(status().is(400));
    }


}