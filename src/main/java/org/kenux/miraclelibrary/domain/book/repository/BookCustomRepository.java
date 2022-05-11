package org.kenux.miraclelibrary.domain.book.repository;

import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.web.book.dto.request.BookSearchFilter;

import java.time.LocalDate;
import java.util.List;

public interface BookCustomRepository {

    List<Book> findBookByFilter(BookSearchFilter bookSearchFilter);

    List<Book> findNewBookWithinOneMonth(LocalDate time);
}
