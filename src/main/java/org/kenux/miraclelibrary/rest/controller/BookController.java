package org.kenux.miraclelibrary.rest.controller;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.rest.dto.BookListResponse;
import org.kenux.miraclelibrary.rest.dto.BookRegisterRequest;
import org.kenux.miraclelibrary.service.BookService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/register")
    public String registerForm() {
        return "books/registerForm";
    }

    @PostMapping(value = "/register")
    public String registerBook(@Validated @ModelAttribute BookRegisterRequest bookRegisterRequest) {
        bookService.registerNewBook(bookRegisterRequest);
        return "redirect:/books";
    }

    @GetMapping
    public String searchBook(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        final List<Book> books = bookService.searchBook(keyword);
        final List<BookListResponse> bookListResponseList = books.stream()
                .map(BookListResponse::of)
                .collect(Collectors.toList());
        model.addAttribute("bookList", bookListResponseList);
        return "books/book-list";
    }
}
