package org.kenux.miraclelibrary.web.book.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.service.BookService;
import org.kenux.miraclelibrary.web.book.api.BookRestController;
import org.kenux.miraclelibrary.web.book.dto.request.BookAddRequest;
import org.kenux.miraclelibrary.web.book.dto.response.BookDetailResponse;
import org.kenux.miraclelibrary.web.book.dto.response.BookResponse;
import org.kenux.miraclelibrary.web.book.dto.response.NewBookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
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

@WebMvcTest(controllers = {BookRestController.class})
@Import(HttpEncodingAutoConfiguration.class)
class BookItemRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    ObjectMapper mapper;

    @Test
    @DisplayName("POST /api/books/register : ?????? ?????? ?????? ???????????? 400 ??????")
    void registerBook_failed() throws Exception {
        // given
        BookAddRequest bookAddRequest = BookAddRequest.builder()
                .author("author")
                .isbn("isbn")
                .build();

        // when
        RequestBuilder request = MockMvcRequestBuilders.post("/api/books/register")
                .content(convertToJson(bookAddRequest))
                .contentType(MediaType.APPLICATION_JSON);

        ResultActions result = mockMvc.perform(request);

        // then
        result.andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    @DisplayName("POST /api/books/register : ??? ?????? ???????????? ???????????? status 200??? ??????????????? ????????????.")
    void registerBook_success() throws Exception {
//        // given
//        BookAddRequest bookAddRequest = BookAddRequest.builder()
//                .title("title")
//                .author("author")
//                .isbn("isbn")
//                .category(BookCategory.ESSAY)
//                .publishDate("2022-1-19")
//                .build();
//        given(bookService.addNewBook(any())).willReturn(1L);
//
//        // when
//        RequestBuilder request = MockMvcRequestBuilders.post("/api/books/register")
//                .content(convertToJson(bookAddRequest))
//                .contentType(MediaType.APPLICATION_JSON);
//
//        ResultActions result = mockMvc.perform(request);
//
//        // then
//        result.andExpect(status().isOk())
//                .andExpect(content().string("1"))
//                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/books : parameter ?????? ??????")
    void searchBook() throws Exception {
        // given
        Book book = Book.builder()
//                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
//                .status(BookStatus.RENTABLE)
                .category(BookCategory.ESSAY)
                .publishDate(LocalDate.of(2022, 1, 1))
                .build();
        List<BookResponse> bookResponses = Collections.singletonList(BookResponse.from(book));
        given(bookService.searchBookByFilter(any())).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/books"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookResponses)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/books?keyword='xxx' : ???????????? ?????? ??????")
    void searchBook_????????????????????????() throws Exception {
        Book book = Book.builder()
//                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
//                .status(BookStatus.RENTABLE)
                .category(BookCategory.ESSAY)
                .publishDate(LocalDate.of(2022, 1, 1))
                .build();
        List<BookResponse> bookResponses = Collections.singletonList(BookResponse.from(book));
        given(bookService.searchBookByFilter(any())).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/api/books")
                        .param("keyword", "title"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookResponses)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/books?category='xxx' : ??????????????? ?????? ??????")
    void searchBook_???????????????????????????() throws Exception {
        Book book = Book.builder()
//                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
//                .status(BookStatus.RENTABLE)
                .category(BookCategory.ESSAY)
                .publishDate(LocalDate.of(2022, 1, 1))
                .build();
        List<BookResponse> bookResponses = Collections.singletonList(BookResponse.from(book));
        given(bookService.searchBookByFilter(any())).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/api/books")
                        .param("category", String.valueOf(BookCategory.ESSAY)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookResponses)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/books?category='xxx' : ?????????,???????????? ???????????? ??????")
    void searchBook_??????????????????????????????????????????() throws Exception {
        Book book = Book.builder()
//                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
//                .status(BookStatus.RENTABLE)
                .category(BookCategory.ESSAY)
                .publishDate(LocalDate.of(2022, 1, 1))
                .build();
        List<BookResponse> bookResponses = Collections.singletonList(BookResponse.from(book));
        given(bookService.searchBookByFilter(any())).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(
                get("/api/books")
                        .param("keyword", "title")
                        .param("category", String.valueOf(BookCategory.ESSAY)));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookResponses)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/books/detail/xx: ????????????, ?????? ???????????? ??????")
    void getBookDetail() throws Exception {
        // given
        Book book = Book.builder()
//                .id(1L)
                .title("title")
                .author("author")
                .isbn("isbn")
//                .status(BookStatus.RENTABLE)
                .category(BookCategory.ESSAY)
                .summary("???????????????")
                .cover("???????????????")
                .publishDate(LocalDate.of(2022, 1, 1))
                .build();
        BookDetailResponse bookDetailResponse = BookDetailResponse.from(book);
        given(bookService.getBookDetail(any())).willReturn(book);
        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/books/detail/1"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookDetailResponse)))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /api/books/new-book : ?????? ?????? ????????? ?????? ????????????")
    void getNewBooks() throws Exception {
        Book book = Book.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .category(BookCategory.ESSAY)
                .summary("???????????????")
                .cover("???????????????")
                .publishDate(LocalDate.of(2022, 1, 1))
                .build();
        ReflectionTestUtils.setField(book, "id", 1L);

        NewBookResponse newBookResponse = NewBookResponse.from(book);

        List<NewBookResponse> bookResponses = Collections.singletonList(newBookResponse);
        given(bookService.getNewBooks()).willReturn(Collections.singletonList(book));

        // when
        final ResultActions resultActions = mockMvc.perform(get("/api/books/new-book"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().string(convertToJson(bookResponses)))
                .andDo(print());
    }

    private String convertToJson(Object value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }

}