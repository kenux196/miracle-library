package org.kenux.miraclelibrary.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookItem;
import org.kenux.miraclelibrary.domain.book.repository.BookInfoRepository;
import org.kenux.miraclelibrary.domain.book.repository.BookRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.kenux.miraclelibrary.global.exception.ErrorCode;
import org.kenux.miraclelibrary.web.book.dto.request.BookSearchFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookInfoRepository bookInfoRepository;

    public Long addNewBook(Book book) {
        return bookInfoRepository.save(book).getId();
    }

    public List<Book> getNewBooks() {
        return bookInfoRepository.findNewBookWithinOneMonth();
    }

    public List<Book> searchBookByFilter(BookSearchFilter filter) {
        return bookInfoRepository.findBookByFilter(filter);
    }

    public List<Book> getAllBooks() {
        return bookInfoRepository.findAll();
    }

    public Long updateBook(Long id, Book book) {
        final Book findBook = getBookInfo(id);
        findBook.update(book);
        return findBook.getId();
    }

    public Book getBookDetail(Long id) {
        return getBookInfo(id);
    }

    private Book getBookInfo(Long id) {
        return bookInfoRepository.findById(id)
                .orElseThrow(() -> new CustomException((ErrorCode.BOOK_NOT_FOUND)));
    }

    private BookItem getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
    }
}