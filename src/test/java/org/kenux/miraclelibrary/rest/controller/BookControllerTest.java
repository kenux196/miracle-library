package org.kenux.miraclelibrary.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.exception.ErrorCode;
import org.kenux.miraclelibrary.rest.dto.BookRegisterRequest;
import org.kenux.miraclelibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {BookController.class})
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Test
    void 책_등록_페이지_로딩() throws Exception {
        // given
        // when
        RequestBuilder request = get("/books/register");
        ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(status().isOk())
                .andExpect(view().name("books/registerForm"))
                .andDo(print());
    }

    @Test
    void 책_등록_실패_제목_미입력() throws Exception {
        // when
        RequestBuilder request = MockMvcRequestBuilders
                .post("/books/register")
                .param("author", "author")
                .param("isbn", "isbn");
        ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(status().is4xxClientError())
                .andDo(print());
    }

    @Test
    void 책_등록_성공() throws Exception {
        // given
        given(bookService.registerNewBook(any())).willReturn(1L);

        // when
        RequestBuilder request = MockMvcRequestBuilders
                .post("/books/register")
                .param("title", "title")
                .param("author", "author")
                .param("isbn", "isbn");
        ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(status().is3xxRedirection())
                .andDo(print());
    }

    @Test
    @DisplayName("북 전체 리스트 가져오기")
    void test_getAllBooks() throws Exception {
        // given
//        given(bookService.searchBook())

        // when
        final ResultActions resultActions = mockMvc.perform(get("/books"));

        // then
        resultActions.andExpect(status().isOk())
                .andDo(print());
    }

}