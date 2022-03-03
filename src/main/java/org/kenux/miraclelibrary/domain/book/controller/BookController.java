package org.kenux.miraclelibrary.domain.book.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kenux.miraclelibrary.domain.book.controller.request.BookAddRequest;
import org.kenux.miraclelibrary.domain.book.controller.response.BookDetailResponse;
import org.kenux.miraclelibrary.domain.book.controller.response.BookResponse;
import org.kenux.miraclelibrary.domain.book.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
        return "/views/books/books";
    }

    @GetMapping("/add")
    public String bookAddForm() {
        return "/views/books/book-add-form";
    }

    @PostMapping("/add")
    public String addNewBook(BookAddRequest bookAddRequest) {
        log.info("will add book info = {}", bookAddRequest);
        Long bookId = bookService.addNewBook(bookAddRequest);
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        final BookDetailResponse book = bookService.getBookDetail(id);
        model.addAttribute("book", book);
        return "/views/books/book";
    }

    @GetMapping("/{id}/edit")
    public String getEditBookForm(@PathVariable("id") Long bookId, Model model) {
        final BookDetailResponse bookDetail = bookService.getBookDetail(bookId);
        model.addAttribute("book", bookDetail);
        return "/views/books/book-edit-form";
    }
}
