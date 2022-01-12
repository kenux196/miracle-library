package org.kenux.miraclelibrary.rest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.enums.BookStatus;
import org.kenux.miraclelibrary.rest.dto.BookListResponse;
import org.kenux.miraclelibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {BookController.class})
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    private ObjectMapper mapper = new ObjectMapper();

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
        result.andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());
    }

    @Test
    @DisplayName("북 전체 리스트 가져오기")
    void test_getAllBooks() throws Exception {
        // given
        Book book = Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .status(BookStatus.RENTABLE)
                .createDate(LocalDateTime.now())
                .build();
        List<BookListResponse> bookListResponses = Collections.singletonList(BookListResponse.of(book));
        given(bookService.searchBook(null)).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/books"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(bookListResponses)))
                .andDo(print());
    }

    @Test
    @DisplayName("사용자가 입력한 키워드에 따른 책 검색 리스트 가져온다.")
    void test_searchBook() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .status(BookStatus.RENTABLE)
                .createDate(LocalDateTime.now())
                .build();
        List<BookListResponse> bookListResponses = Collections.singletonList(BookListResponse.of(book));
        given(bookService.searchBook(any())).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/books"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(bookListResponses)))
                .andDo(print());
    }

    @Test
    @DisplayName("api: /books/new-book => 신간 서적 리스트 조회 요청처리")
    void 신간_서적_리스트_요청처리() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .status(BookStatus.RENTABLE)
                .createDate(LocalDateTime.now())
                .build();

        List<BookListResponse> bookListResponses = Collections.singletonList(BookListResponse.of(book));
        given(bookService.getNewBooks()).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/books/new-book"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(mapper.writeValueAsString(bookListResponses)))
                .andDo(print());
    }


}