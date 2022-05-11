package org.kenux.miraclelibrary.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.book.controller.request.BookAddRequest;
import org.kenux.miraclelibrary.domain.book.controller.request.BookSearchFilter;
import org.kenux.miraclelibrary.domain.book.controller.request.BookUpdateRequest;
import org.kenux.miraclelibrary.domain.book.controller.response.BookDetailResponse;
import org.kenux.miraclelibrary.domain.book.controller.response.BookResponse;
import org.kenux.miraclelibrary.domain.book.controller.response.NewBookResponse;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.kenux.miraclelibrary.domain.book.repository.BookRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.kenux.miraclelibrary.global.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;

    // TODO : 테스트용 나중에 삭제할 것.   - sky 2022/03/01
    @PostConstruct
    void init() {
        for (int i = 0; i < 10; i++) {
            Book newBook = Book.builder()
                    .title("테스트 북 " + i)
                    .author("김작가")
                    .category(BookCategory.ESSAY)
                    .isbn("isbn-" + i)
                    .publishDate(LocalDate.of(2022, 5, i + 1))
                    .build();
            newBook.changeStatus(BookStatus.RENTABLE);
            bookRepository.save(newBook);
        }
    }

    public Long addNewBook(BookAddRequest bookAddRequest) {
        final Book book = bookAddRequest.toEntity();
        return bookRepository.save(book).getId();
    }

    public List<NewBookResponse> getNewBooks() {
        final List<Book> newBooks = bookRepository.findNewBookWithinOneMonth(LocalDate.now());
        return newBooks.stream()
                .map(NewBookResponse::from)
                .collect(Collectors.toList());
    }

    public List<BookResponse> searchBookByFilter(BookSearchFilter filter) {
        return bookRepository.findBookByFilter(filter).stream()
                .filter(Book::isHeldBook)
                .map(BookResponse::from)
                .collect(Collectors.toList());
    }

    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookResponse::from)
                .collect(Collectors.toList());
    }

    public Long updateBook(BookUpdateRequest bookUpdateRequest) {
        final Book book = getBook(bookUpdateRequest.getId());
        book.update(bookUpdateRequest.toEntity());
        return book.getId();
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