package org.kenux.miraclelibrary.web.book.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.service.BookService;
import org.kenux.miraclelibrary.web.book.dto.request.BookAddRequest;
import org.kenux.miraclelibrary.web.book.dto.request.BookUpdateRequest;
import org.kenux.miraclelibrary.web.book.dto.response.BookDetailResponse;
import org.kenux.miraclelibrary.web.book.dto.response.BookResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
        List<Book> bookList = bookService.getAllBooks();
        List<BookResponse> allBooks = bookList.stream()
                .map(BookResponse::from)
                .collect(Collectors.toList());
        model.addAttribute("books", allBooks);
        return "views/books/books";
    }

    @GetMapping("/add")
    public String bookAddForm(Model model) {
        model.addAttribute("book", new BookAddRequest());
        return "views/books/book-add-form";
    }

    @PostMapping("/add")
    public String addNewBook(@Valid BookAddRequest bookAddRequest) {
        log.info("will add book info = {}", bookAddRequest);
        Long bookId = bookService.addNewBook(bookAddRequest.toEntity());
        return "redirect:/books/" + bookId;
    }

    @GetMapping("/{id}")
    public String getBook(@PathVariable Long id, Model model) {
        final Book book = bookService.getBookDetail(id);
        model.addAttribute("book", BookDetailResponse.from(book));
        return "views/books/book";
    }

    @GetMapping("/{id}/edit")
    public String getEditBookForm(@PathVariable("id") Long bookId, Model model) {
        final Book book = bookService.getBookDetail(bookId);
        model.addAttribute("book", BookDetailResponse.from(book));
        return "views/books/book-edit-form";
    }

    @PostMapping("/{id}/edit")
    public String editBook(@PathVariable("id") Long id,
                           BookUpdateRequest bookUpdateRequest) {
        Long bookId = bookService.updateBook(id, bookUpdateRequest.toEntity());
        return "redirect:/books/" + bookId;
    }
}
