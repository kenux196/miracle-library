package org.kenux.miraclelibrary.domain.book.controller;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.dto.BookListResponse;
import org.kenux.miraclelibrary.domain.book.dto.BookRegisterRequest;
import org.kenux.miraclelibrary.domain.book.dto.BookSearchFilter;
import org.kenux.miraclelibrary.domain.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/register")
    public ResponseEntity<Long> registerBook(@Valid @RequestBody BookRegisterRequest bookRegisterRequest) {
        final Long bookId = bookService.registerNewBook(bookRegisterRequest);
        return ResponseEntity.ok(bookId);
    }

    @GetMapping
    public ResponseEntity<?> searchBook(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "category", required = false) String category) {
        BookSearchFilter searchFilter = BookSearchFilter.builder()
                .keyword(keyword)
                .category(category != null ? BookCategory.valueOf(category) : null)
                .build();
        final List<Book> books = bookService.searchBookByFilter(searchFilter);
        final List<BookListResponse> bookListResponseList = bookListResponsesOf(books);
        return ResponseEntity.ok(bookListResponseList);
    }

    @GetMapping("/new-book")
    public ResponseEntity<?> getNewBooks() {
        final List<Book> books = bookService.getNewBooks();
        final List<BookListResponse> bookListResponseList = bookListResponsesOf(books);
        return ResponseEntity.ok(bookListResponseList);
    }

    private List<BookListResponse> bookListResponsesOf(List<Book> books) {
        return books.stream()
                .map(BookListResponse::of)
                .collect(Collectors.toList());
    }
}
