package org.kenux.miraclelibrary.domain.book.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookInfo;
import org.kenux.miraclelibrary.domain.book.repository.BookInfoRepository;
import org.kenux.miraclelibrary.domain.book.repository.BookRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.kenux.miraclelibrary.global.exception.ErrorCode;
import org.kenux.miraclelibrary.web.book.dto.request.BookAddRequest;
import org.kenux.miraclelibrary.web.book.dto.request.BookSearchFilter;
import org.kenux.miraclelibrary.web.book.dto.request.BookUpdateRequest;
import org.kenux.miraclelibrary.web.book.dto.response.BookDetailResponse;
import org.kenux.miraclelibrary.web.book.dto.response.BookResponse;
import org.kenux.miraclelibrary.web.book.dto.response.NewBookResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookInfoRepository bookInfoRepository;

//    // TODO : 테스트용 나중에 삭제할 것.   - sky 2022/03/01
//    @PostConstruct
//    void init() {
//        for (int i = 0; i < 10; i++) {
//            Book newBook = Book.builder()
//                    .title("테스트 북 " + i)
//                    .author("김작가")
//                    .category(BookCategory.ESSAY)
//                    .isbn("isbn-" + i)
//                    .publishDate(LocalDate.of(2022, 5, i + 1))
//                    .build();
//            newBook.changeStatus(BookStatus.RENTABLE);
//            bookRepository.save(newBook);
//        }
//    }

    public Long addNewBook(BookAddRequest bookAddRequest) {
        final BookInfo bookInfo = bookAddRequest.toEntity();
        return 1L;
        // TODO : need modify   - sky 2022/05/11
//        return bookRepository.save(bookInfo).getId();
    }

    public Long addNewBook2(BookInfo bookInfo) {
        return bookInfoRepository.save(bookInfo).getId();
    }

    public List<NewBookResponse> getNewBooks() {
        final List<BookInfo> newBooks = bookInfoRepository.findNewBookPublishDateWithinOneMonth(LocalDate.now());
        return newBooks.stream()
                .map(NewBookResponse::from)
                .collect(Collectors.toList());
    }

    public List<BookResponse> searchBookByFilter(BookSearchFilter filter) {
        return bookInfoRepository.findBookByFilter(filter).stream()
                .map(BookResponse::from)
                .collect(Collectors.toList());
    }

    public List<BookResponse> getAllBooks() {
        return Collections.emptyList();
        // TODO : modify   - sky 2022/05/12
//        return bookRepository.findAll().stream()
//                .map(BookResponse::from)
//                .collect(Collectors.toList());
    }

    public Long updateBook(BookUpdateRequest bookUpdateRequest) {
        // TODO : need modify   - sky 2022/05/11
//        final Book book = getBook(bookUpdateRequest.getId());
//        book.update(bookUpdateRequest.toEntity());
//        return book.getId();
        return 1L;
    }

    public BookDetailResponse getBookDetail(Long id) {
//        final BookInfo book = getBook(id);
//        return BookDetailResponse.from(book);
        return null;
    }

    private Book getBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
    }
}