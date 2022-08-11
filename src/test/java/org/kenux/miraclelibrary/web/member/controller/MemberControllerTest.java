package org.kenux.miraclelibrary.web.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.domain.member.domain.MemberStatus;
import org.kenux.miraclelibrary.domain.member.service.MemberFindService;
import org.kenux.miraclelibrary.domain.member.service.MemberJoinService;
import org.kenux.miraclelibrary.testutils.TestUtils;
import org.kenux.miraclelibrary.web.member.dto.MemberJoinRequestBuilder;
import org.kenux.miraclelibrary.web.member.dto.request.MemberJoinRequest;
import org.kenux.miraclelibrary.web.member.dto.response.MemberBasicInfoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@Import(HttpEncodingAutoConfiguration.class)
class MemberControllerTest {


    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberJoinService memberJoinService;

    @MockBean
    MemberFindService memberFindService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ResourceLoader resourceLoader;

    @Test
    @DisplayName("joinMember - 회원 가입(from json)")
    void joinMember_fromJson() throws Exception {
        // given
        final String requestBody = TestUtils.readJson(
                resourceLoader,
                "classpath:requests/MemberJoinRequest-success.json");
        given(memberJoinService.join(any())).willReturn(1L);

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/members")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("joinMember - 회원 가입(from Object)")
    void joinMember_fromObject() throws Exception {
        // given
        MemberJoinRequest memberJoinRequest =
                MemberJoinRequestBuilder.build(
                        "member1", "member1@test.com", "010-1234-1234", "password");
        given(memberJoinService.join(any())).willReturn(1L);

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/members")
                .content(convertToJson(memberJoinRequest))
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
        MemberJoinRequest memberJoinRequest =
                MemberJoinRequestBuilder.build(
                        null, null, null, null);

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/members")
                .content(convertToJson(memberJoinRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andDo(print());
    }

    @Test
    @DisplayName("전체 사용자 리스트 가져오기")
    void getAllMembers() throws Exception {
        // given
        List<MemberBasicInfoResponse> response = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MemberBasicInfoResponse basicInfoResponse = MemberBasicInfoResponse.builder()
                    .id((long) i)
                    .name("user" + i)
                    .email("user" + i + "@email.com")
                    .role(MemberRole.CUSTOMER)
                    .status(MemberStatus.NORMAL)
                    .build();
            response.add(basicInfoResponse);
        }

        given(memberFindService.getMembers()).willReturn(response);
        RequestBuilder request = MockMvcRequestBuilders.get("/members")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        // when
        ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().string(convertToJson(response)))
                .andDo(print());

    }

    private String convertToJson(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }
}