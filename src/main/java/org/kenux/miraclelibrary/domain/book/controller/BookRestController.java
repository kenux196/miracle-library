package org.kenux.miraclelibrary.domain.book.controller;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.dto.BookDetailResponse;
import org.kenux.miraclelibrary.domain.book.dto.BookRegisterRequest;
import org.kenux.miraclelibrary.domain.book.dto.BookResponse;
import org.kenux.miraclelibrary.domain.book.dto.BookSearchFilter;
import org.kenux.miraclelibrary.domain.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookRestController {

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
                .category(category != null ? BookCategory.getBookCategory(category) : null)
                .build();
        final List<BookResponse> bookListResponse = bookService.searchBookByFilter(searchFilter);
        return ResponseEntity.ok(bookListResponse);
    }

    @GetMapping(value = "/detail/{id}")
    public ResponseEntity<?> getBookDetail(@PathVariable(value = "id") Long id) {
        final BookDetailResponse bookDetail = bookService.getBookDetail(id);
        return ResponseEntity.ok(bookDetail);
    }

    @GetMapping("/new-book")
    public ResponseEntity<?> getNewBooks() {
        return ResponseEntity.ok(bookService.getNewBooks());
    }
}
