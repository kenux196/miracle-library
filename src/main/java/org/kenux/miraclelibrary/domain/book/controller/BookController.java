package org.kenux.miraclelibrary.domain.book.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kenux.miraclelibrary.domain.book.controller.response.BookResponse;
import org.kenux.miraclelibrary.domain.book.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @GetMapping
    public String booksMainPage(Model model) {
        List<BookResponse> allBooks = bookService.getAllBooks();
        model.addAttribute("books", allBooks);
        return "/views/books/books-main";
    }
}
