package org.kenux.miraclelibrary.domain.book.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.kenux.miraclelibrary.domain.book.dto.BookDetailResponse;
import org.kenux.miraclelibrary.domain.book.dto.BookRegisterRequest;
import org.kenux.miraclelibrary.domain.book.dto.BookResponse;
import org.kenux.miraclelibrary.domain.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {BookController.class})
@Import(HttpEncodingAutoConfiguration.class)
class BookControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    ObjectMapper mapper;

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
                .category(BookCategory.ESSAY)
                .publicationDate(LocalDate.now())
                .build();
        given(bookService.registerNewBook(any())).willReturn(1L);

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/books/register")
                .content(convertToJson(bookRegisterRequest))
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(status().isOk())
                .andExpect(content().string("1"))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /books : parameter 없는 경우")
    void searchBook() throws Exception {
        // given
        Book book = Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .status(BookStatus.RENTABLE)
                .category(BookCategory.ESSAY)
                .publicationDate(LocalDate.of(2022, 1, 1))
                .build();
        List<BookResponse> bookResponses = Collections.singletonList(BookResponse.of(book));
        given(bookService.searchBookByFilter(any())).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/books"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookResponses)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /books?keyword='xxx' : 키워드만 있는 경우")
    void searchBook_키워드만있는경우() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .status(BookStatus.RENTABLE)
                .category(BookCategory.ESSAY)
                .publicationDate(LocalDate.of(2022, 1, 1))
                .build();
        List<BookResponse> bookResponses = Collections.singletonList(BookResponse.of(book));
        given(bookService.searchBookByFilter(any())).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/books")
                        .param("keyword", "title"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookResponses)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /books?category='xxx' : 카테고리만 있는 경우")
    void searchBook_카테고리만있는경우() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .status(BookStatus.RENTABLE)
                .category(BookCategory.ESSAY)
                .publicationDate(LocalDate.of(2022, 1, 1))
                .build();
        List<BookResponse> bookResponses = Collections.singletonList(BookResponse.of(book));
        given(bookService.searchBookByFilter(any())).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/books")
                        .param("category", "essay"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookResponses)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /books?category='xxx' : 키워드,카테고리 모두있는 경우")
    void searchBook_키워드와카테고리모두있는경우() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .status(BookStatus.RENTABLE)
                .category(BookCategory.ESSAY)
                .publicationDate(LocalDate.of(2022, 1, 1))
                .build();
        List<BookResponse> bookResponses = Collections.singletonList(BookResponse.of(book));
        given(bookService.searchBookByFilter(any())).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/books")
                        .param("keyword", "title")
                        .param("category", "essay"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookResponses)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /books/detail/xx: 정상이면, 책의 상세정보 반환")
    void getBookDetail() throws Exception {
        // given
        Book book = Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .status(BookStatus.RENTABLE)
                .category(BookCategory.ESSAY)
                .content("책소개내용")
                .cover("이미지경로")
                .publicationDate(LocalDate.of(2022, 1, 1))
                .build();
        BookDetailResponse bookDetailResponse = BookDetailResponse.of(book);
        given(bookService.getBookDetail(any())).willReturn(bookDetailResponse);
        // when
        final ResultActions resultActions = mockMvc.perform(get("/books/detail/1"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookDetailResponse)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /books/new-book : 신간 서적 리스트 조회 요청처리")
    void getNewBooks() throws Exception {
        Book book = Book.builder()
                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
                .status(BookStatus.RENTABLE)
                .publicationDate(LocalDate.of(2022, 1, 1))
                .build();

        List<BookResponse> bookResponses = Collections.singletonList(BookResponse.of(book));
        given(bookService.getNewBooks()).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/books/new-book"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookResponses)))
                .andDo(print());
    }

    private String convertToJson(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }

}