package org.kenux.miraclelibrary.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.dto.BookRegisterRequest;
import org.kenux.miraclelibrary.domain.book.dto.BookSearchFilter;
import org.kenux.miraclelibrary.domain.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Long registerNewBook(BookRegisterRequest bookRegisterRequest) {
        final Book book = bookRegisterRequest.toEntity();
        return bookRepository.save(book).getId();
    }

    public List<Book> getNewBooks() {
        return bookRepository.findNewBookWithinOneMonth(LocalDate.now());
    }

    public List<Book> searchBookByFilter(BookSearchFilter filter) {
        return bookRepository.findBookByFilter(filter).stream()
                .filter(Book::isHeldBook)
                .collect(Collectors.toList());
    }
}