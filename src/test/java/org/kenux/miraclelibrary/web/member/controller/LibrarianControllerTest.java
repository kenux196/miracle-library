package org.kenux.miraclelibrary.web.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.member.service.LibrarianJoinService;
import org.kenux.miraclelibrary.testutils.TestUtils;
import org.kenux.miraclelibrary.web.member.dto.LibrarianJoinRequestBuilder;
import org.kenux.miraclelibrary.web.member.dto.request.LibrarianJoinRequest;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibrarianController.class)
@Import(HttpEncodingAutoConfiguration.class)
class LibrarianControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    LibrarianJoinService librarianJoinService;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    ResourceLoader resourceLoader;

    @Test
    @DisplayName("join - 사서 추가(from json)")
    void add_fromJson() throws Exception {
        // given
        final String requestBody = TestUtils.readJson(
                resourceLoader,
                "classpath:requests/MemberJoinRequest-success.json");
        given(librarianJoinService.add(any())).willReturn(1L);

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/librarian")
                .content(requestBody)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("join - 사서 추가(from Object)")
    void add_fromObject() throws Exception {
        // given
        LibrarianJoinRequest librarianJoinRequest =
                LibrarianJoinRequestBuilder.build(
                        "member1", "member1@test.com", "010-1234-1234", "password");
        given(librarianJoinService.add(any())).willReturn(1L);

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/librarian")
                .content(convertToJson(librarianJoinRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    @DisplayName("validateRequest: 사용자 정보 누락 시, 예외 발생")
    void validateRequest() throws Exception {
        // given
        LibrarianJoinRequest librarianJoinRequest =
                LibrarianJoinRequestBuilder.build(
                        null, null, null, null);

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/librarian")
                .content(convertToJson(librarianJoinRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        // then
        mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andDo(print());
    }

    private String convertToJson(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }
}