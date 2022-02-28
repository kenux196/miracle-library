package org.kenux.miraclelibrary.domain.book.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.dto.BookRegisterRequest;
import org.kenux.miraclelibrary.domain.book.dto.BookResponse;
import org.kenux.miraclelibrary.domain.book.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    // TODO : 테스트용 나중에 삭제할 것.   - sky 2022/03/01
    @PostConstruct
    void init() {
        for (int i = 0; i < 10; i++) {
            BookRegisterRequest newBook = BookRegisterRequest.builder()
                    .title("Book " + i)
                    .author("김작가")
                    .category(BookCategory.ESSAY)
                    .isbn("isbn-" + i)
                    .publicationDate(LocalDate.of(2021, 12, i + 1))
                    .build();
            bookService.registerNewBook(newBook);
        }
    }

    @GetMapping
    public String booksMainPage(Model model) {
        List<BookResponse> allBooks = bookService.getAllBooks();
        model.addAttribute("books", allBooks);
        return "/views/books/books-main";
    }
}
