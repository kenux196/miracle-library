package org.kenux.miraclelibrary.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.dto.*;
import org.kenux.miraclelibrary.domain.book.repository.BookRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.kenux.miraclelibrary.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    public Long registerNewBook(BookRegisterRequest bookRegisterRequest) {
        final Book book = bookRegisterRequest.toEntity();
        return bookRepository.save(book).getId();
    }

    public List<BookResponse> getNewBooks() {
        final List<Book> newBooks = bookRepository.findNewBookWithinOneMonth(LocalDate.now());
        return newBooks.stream()
                .map(BookResponse::from)
                .collect(Collectors.toList());
    }

    public List<BookResponse> searchBookByFilter(BookSearchFilter filter) {
        return bookRepository.findBookByFilter(filter).stream()
                .filter(Book::isHeldBook)
                .map(BookResponse::from)
                .collect(Collectors.toList());
    }

    public Book updateBook(BookUpdateRequest bookUpdateRequest) {
        final Book book = getBook(bookUpdateRequest.getId());

        if (bookUpdateRequest.getTitle() != null) {
            book.changeTitle(bookUpdateRequest.getTitle());
        }
        if (bookUpdateRequest.getAuthor() != null) {
            book.changeAuthor(bookUpdateRequest.getAuthor());
        }
        if (bookUpdateRequest.getIsbn() != null) {
            book.changeIsbn(bookUpdateRequest.getIsbn());
        }
        if (bookUpdateRequest.getCategory() != null) {
            book.changeCategory(bookUpdateRequest.getCategory());
        }
        if (bookUpdateRequest.getContent() != null) {
            book.changeContent(bookUpdateRequest.getContent());
        }
        if (bookUpdateRequest.getCover() != null) {
            book.changeCover(bookUpdateRequest.getCover());
        }
        if (bookUpdateRequest.getPublicationDate() != null) {
            book.changePublicationDate(bookUpdateRequest.getPublicationDate());
        }
        return book;
    }

    public BookDetailResponse getBookDetail(Long id) {
        final Book book = getBook(id);
        return BookDetailResponse.from(book);
    }

    private Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
    }
}