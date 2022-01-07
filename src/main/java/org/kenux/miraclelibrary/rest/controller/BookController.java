package org.kenux.miraclelibrary.rest.controller;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.rest.dto.BookListResponse;
import org.kenux.miraclelibrary.rest.dto.BookRegisterRequest;
import org.kenux.miraclelibrary.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/register")
    public ResponseEntity<Long> registerBook(@Validated @ModelAttribute BookRegisterRequest bookRegisterRequest) {
        final Long bookId = bookService.registerNewBook(bookRegisterRequest);
        return ResponseEntity.ok(bookId);
    }

    @GetMapping
    public ResponseEntity<?> searchBook(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        final List<Book> books = bookService.searchBook(keyword);
        final List<BookListResponse> bookListResponseList = books.stream()
                .map(BookListResponse::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(bookListResponseList);
    }
}
