package org.kenux.miraclelibrary.domain.book.repository;

import org.kenux.miraclelibrary.domain.book.domain.BookInfo;
import org.kenux.miraclelibrary.web.book.dto.request.BookSearchFilter;

import java.util.List;

public interface BookInfoCustomRepository {

    List<BookInfo> findBookByFilter(BookSearchFilter bookSearchFilter);

    List<BookInfo> findNewBookWithinOneMonth();
}
