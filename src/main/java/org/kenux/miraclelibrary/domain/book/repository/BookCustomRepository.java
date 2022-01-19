package org.kenux.miraclelibrary.domain.book.repository;

import org.kenux.miraclelibrary.domain.book.domain.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookCustomRepository {

    List<Book> findAllByKeyword(String keyword);

    List<Book> findNewBookWithinOneMonth(LocalDate time);
}
