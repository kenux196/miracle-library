package org.kenux.miraclelibrary.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.rest.dto.MemberJoinRequest;
import org.kenux.miraclelibrary.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    ObjectMapper mapper = new ObjectMapper();

    @Test
    @DisplayName("회원 가입")
    void test_joinMember() throws Exception {
        // given
        MemberJoinRequest memberJoinRequest = new MemberJoinRequest("user", "user@test.com", "password");
        given(memberService.join(any())).willReturn(1L);

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/member/join")
                .content(mapper.writeValueAsString(memberJoinRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("사용자 정보 누락 시, 예외 발생")
    void test_validateRequest_whenJoinMember() throws Exception {
        // given
        MemberJoinRequest memberJoinRequest = MemberJoinRequest.builder()
                .email("user@test.com")
                .password("password")
                .build();

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/member/join")
                .content(mapper.writeValueAsString(memberJoinRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andDo(print());
    }

}