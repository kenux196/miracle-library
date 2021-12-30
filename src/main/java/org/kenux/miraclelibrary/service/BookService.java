package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.repository.BookRepository;
import org.kenux.miraclelibrary.rest.dto.BookRegisterRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public Long registerNewBook(BookRegisterRequestDto bookRegisterRequestDto) {
        final Book book = bookRegisterRequestDto.toEntity();
        return bookRepository.save(book).getId();
    }

    public List<Book> searchBook(String keyword) {
        return bookRepository.findAllByKeyword(keyword);
    }
}