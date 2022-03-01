package org.kenux.miraclelibrary.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.book.controller.request.BookRegisterRequest;
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
                    .publicationDate(LocalDate.of(2021, 12, i + 1))
                    .build();
            newBook.changeStatus(BookStatus.RENTABLE);
            bookRepository.save(newBook);
        }
    }

    public Long registerNewBook(BookRegisterRequest bookRegisterRequest) {
        final Book book = bookRegisterRequest.toEntity();
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