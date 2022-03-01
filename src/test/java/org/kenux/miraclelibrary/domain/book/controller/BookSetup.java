package org.kenux.miraclelibrary.domain.book.controller;

import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.kenux.miraclelibrary.domain.book.controller.request.BookRegisterRequest;
import org.kenux.miraclelibrary.domain.book.repository.BookRepository;
import org.springframework.stereotype.Component;

@Component
public class BookSetup {
    private final BookRepository bookRepository;

    public BookSetup(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book saveBook(BookRegisterRequest bookRegisterRequest) {
        final Book book = bookRegisterRequest.toEntity();
        book.changeStatus(BookStatus.RENTABLE);
        return bookRepository.save(book);
    }
}