package org.kenux.miraclelibrary.web.book.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookItem;
import org.kenux.miraclelibrary.domain.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.util.ReflectionTestUtils;
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
class BookItemControllerTest {

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
        Book book = createBookInfo(1L, 1);
        given(bookService.getAllBooks()).willReturn(Collections.singletonList(book));

        // when
        ResultActions resultActions = mockMvc.perform(get("/books").param("keyword", ""));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("views/books/books"))
                .andDo(print());
    }

    @Test
    @DisplayName("Get /books/add 요청은 /views/books/book-add-form 페이지로 이동한다.")
    void GET_books_add() throws Exception {
        // when
        ResultActions resultActions = mockMvc.perform(get("/books/add"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("views/books/book-add-form"));
    }

    @Test
    @DisplayName("POST /books/add 요청은 책을 등록하고 /books 로 리다이렉트한다.")
    void POST_books_add() throws Exception {
        // given
        given(bookService.addNewBook(any())).willReturn(1L);

        // when
        ResultActions resultActions = mockMvc.perform(post("/books/add")
                .param("title", "테스트코드작성법")
                .param("author", "김작가")
                .param("isbn", "1234123")
                .param("publishDate", "2021-12-23")
                .param("category", "ESSAY")
                .param("amount", "1"));

        // then
        resultActions.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books/1"));
    }

    @Test
    @DisplayName("GET /books/{id} 요청은 /views/books/book 상세 페이지로 이동")
    void test_getBookDetail() throws Exception {
        // given
        Book book = createBookInfo(1L, 1);
        given(bookService.getBookDetail(any())).willReturn(book);

        // when
        final ResultActions resultActions = mockMvc.perform(get("/books/" + book.getId()));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("views/books/book"));
    }

    @Test
    @DisplayName("GET /books/{id}/edit 요청은 views/books/edit-book-form 으로 이동")
    void test_edit_book_form() throws Exception {
        // given
        Book book = createBookInfo(1L, 1);
        given(bookService.getBookDetail(any())).willReturn(book);
        given(bookService.updateBook(any(), any())).willReturn(book.getId());

        // when
        final ResultActions resultActions = mockMvc.perform(get("/books/" + book.getId() + "/edit"));

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(view().name("views/books/book-edit-form"));
    }

    private Book createBookInfo(Long id, int count) {
        Book book = Book.builder()
                .title("제목")
                .isbn("isbn")
                .author("저자")
                .publishDate(LocalDate.of(2022, 1, 19))
                .category(BookCategory.IT)
                .build();
        ReflectionTestUtils.setField(book, "id", id);
        for (int i = 0; i < count; i++) {
            book.addBook(BookItem.createNewBook());
        }
        return book;
    }

}