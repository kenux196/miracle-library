package org.kenux.miraclelibrary.domain.book.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.BaseIntegrationTest;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.dto.BookRegisterRequest;
import org.kenux.miraclelibrary.domain.book.dto.BookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private BookSetup bookSetup;

    @Test
    @DisplayName("POST /books/register : 입력 정보 부족 실패이면 400 리턴")
    void registerBook_failed() throws Exception {
        // given
        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .author("author")
                .isbn("isbn")
                .build();

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/books/register")
                .content(convertToJson(bookRegisterRequest))
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("POST /books/register : 책 등록 요청처리 정상이면 status 200과 등록번호를 리턴한다.")
    void registerBook_success() throws Exception {
        // given
        BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .publicationDate(LocalDate.now())
                .category(BookCategory.ESSAY)
                .build();

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/books/register")
                .content(convertToJson(bookRegisterRequest))
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("GET /books : 책 전체 리스트 요청 처리 정상")
    void getAllBooks() throws Exception {
        // given
        List<BookResponse> bookRespons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                    .title("title" + i)
                    .author("author")
                    .isbn("isbn"+ i)
                    .publicationDate(LocalDate.of(2020, 1, 11).plusDays(i))
                    .build();
            final Book book = bookSetup.saveBook(bookRegisterRequest);
            bookRespons.add(BookResponse.of(book));
        }

        // when
        final ResultActions resultActions = mockMvc.perform(get("/books"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookRespons)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /books?keyword='xxx' : 키워드 검색 요청처리")
    void searchBook() throws Exception {
        // given
        List<BookResponse> bookRespons = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                    .title("title" + i)
                    .author("author")
                    .isbn("isbn"+ i)
                    .publicationDate(LocalDate.of(2020, 1, 11).plusDays(i))
                    .build();
            final Book book = bookSetup.saveBook(bookRegisterRequest);
            bookRespons.add(BookResponse.of(book));
        }

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/books")
                        .param("keyword", "title"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookRespons)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /books/new-book : 신간 서적 리스트 조회 요청처리")
    void getNewBooks() throws Exception {
        // given
        List<BookResponse> bookRespons = new ArrayList<>();
        final LocalDate publicationDateLessThanOneMonth = LocalDate.now().minusDays(15);
        for (int i = 0; i < 5; i++) {
            BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                    .title("title" + i)
                    .author("author")
                    .isbn("isbn"+ i)
                    .publicationDate(publicationDateLessThanOneMonth)
                    .build();
            final Book book = bookSetup.saveBook(bookRegisterRequest);
            bookRespons.add(BookResponse.of(book));
        }

        for (int i = 0; i < 5; i++) {
            BookRegisterRequest bookRegisterRequest = BookRegisterRequest.builder()
                    .title("title" + i)
                    .author("author")
                    .isbn("isbn"+ i)
                    .publicationDate(LocalDate.of(2021, 1, 1))
                    .build();
            bookSetup.saveBook(bookRegisterRequest);
        }
        // when
        final ResultActions resultActions = mockMvc.perform(get("/books/new-book"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookRespons)))
                .andDo(print());
    }

    private String convertToJson(Object value) throws JsonProcessingException {
        return objectMapper.writeValueAsString(value);
    }
}