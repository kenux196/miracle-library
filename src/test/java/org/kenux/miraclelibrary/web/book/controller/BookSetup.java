package org.kenux.miraclelibrary.web.book.controller;

import org.kenux.miraclelibrary.domain.book.repository.BookRepository;
import org.springframework.stereotype.Component;

@Component
public class BookSetup {
    private final BookRepository bookRepository;

    public BookSetup(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

//    public Book saveBook(BookAddRequest bookAddRequest) {
//        final Book book = bookAddRequest.toEntity();
//        book.changeStatus(BookStatus.RENTABLE);
//        return bookRepository.save(book);
//    }
}