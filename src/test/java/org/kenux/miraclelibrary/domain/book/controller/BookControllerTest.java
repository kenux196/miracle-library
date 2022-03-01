package org.kenux.miraclelibrary.domain.book.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.book.controller.request.BookAddRequest;
import org.kenux.miraclelibrary.domain.book.controller.response.NewBookResponse;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {BookController.class})
class BookControllerTest {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    @DisplayName("/books 요청에 대해 /views/books/books 페이지로 이동")
    void GET_books() throws Exception {
        // given
        NewBookResponse bookResponse = NewBookResponse.builder()
                .bookId(1L)
                .title("title")
                .author("author")
                .category(BookCategory.ESSAY)
                .build();
        given(bookService.getNewBooks()).willReturn(Collections.singletonList(bookResponse));

        // when
        ResultActions resultActions = mockMvc.perform(get("/books"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(model().size(1))
                .andExpect(view().name("/views/books/books"))
                .andDo(print());
    }

    @Test
    @DisplayName("Get /books/add 요청은 /views/books/book-add-form 페이지로 이동한다.")
    void GET_books_add() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get("/books/add"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("/views/books/book-add-form"));
    }

    @Test
    @DisplayName("POST /books/add 요청은 책을 등록하고 /books 로 리다이렉트한다.")
    void POST_books_add() throws Exception {
        // given
        BookAddRequest bookAddRequest = BookAddRequest.builder()
                .title("title")
                .author("author")
                .isbn("isbn")
                .publishDate(LocalDate.now())
                .category(BookCategory.ESSAY)
                .build();
        given(bookService.registerNewBook(any())).willReturn(1L);

        // when
        ResultActions resultActions = mockMvc.perform(post("/books/add")
                .param("title", "테스트코드작성법")
                .param("author", "김작가")
                .param("isbn", "1234123")
                .param("publishDate", "2021-12-23")
                .param("category", "ESSAY"));

        // then
        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));

    }
}