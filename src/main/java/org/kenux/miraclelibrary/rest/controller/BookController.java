package org.kenux.miraclelibrary.rest.controller;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.rest.dto.BookRegisterRequest;
import org.kenux.miraclelibrary.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
        return "redirect:/";
    }
}
