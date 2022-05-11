package org.kenux.miraclelibrary.web.book.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.service.BookService;
import org.kenux.miraclelibrary.web.book.dto.request.BookAddRequest;
import org.kenux.miraclelibrary.web.book.dto.request.BookUpdateRequest;
import org.kenux.miraclelibrary.web.book.dto.response.BookDetailResponse;
import org.kenux.miraclelibrary.web.book.dto.response.BookResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @ModelAttribute("bookCategories")
    public static BookCategory[] bookCategories() {
        return BookCategory.values();
    }

    @GetMapping
    public String booksMainPage(Model model) {
        List<BookResponse> allBooks = bookService.getAllBooks();
        model.addAttribute("books", allBooks);
        return "views/books/books";
    }

    @GetMapping("/add")
    public String bookAddForm(Model model) {
        model.addAttribute("book", new BookDetailResponse());
        return "views/books/book-add-form";
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
        return "views/books/book";
    }

    @GetMapping("/{id}/edit")
    public String getEditBookForm(@PathVariable("id") Long bookId, Model model) {
        final BookDetailResponse book = bookService.getBookDetail(bookId);
        model.addAttribute("book", book);
        return "views/books/book-edit-form";
    }

    @PostMapping("/{id}/edit")
    public String editBook(BookUpdateRequest bookUpdateRequest) {
        Long bookId = bookService.updateBook(bookUpdateRequest);
        return "redirect:/books/" + bookId;
    }
}
