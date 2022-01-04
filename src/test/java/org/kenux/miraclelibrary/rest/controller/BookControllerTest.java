package org.kenux.miraclelibrary.rest.controller;

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
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.BDDMockito.given;
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
                .andExpect(view().name("books/book-list"))
                .andExpect(model().attributeExists("bookList"))
                .andExpect(model().attribute("bookList", bookListResponses))
                .andDo(print());
    }

}