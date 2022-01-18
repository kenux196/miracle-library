package org.kenux.miraclelibrary.repository.querydsl;

import org.kenux.miraclelibrary.domain.Book;

import java.time.LocalDate;
import java.util.List;

public interface BookCustomRepository {

    List<Book> findAllByKeyword(String keyword);

    List<Book> findNewBookWithinOneMonth(LocalDate time);
}
