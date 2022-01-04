package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.repository.BookRepository;
import org.kenux.miraclelibrary.rest.dto.BookRegisterRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Long registerNewBook(BookRegisterRequest bookRegisterRequest) {
        final Book book = bookRegisterRequest.toEntity();
        return bookRepository.save(book).getId();
    }

    public List<Book> searchBook(String keyword) {
        if (keyword != null) return bookRepository.findAllByKeyword(keyword);
        return bookRepository.findAll();
    }
}