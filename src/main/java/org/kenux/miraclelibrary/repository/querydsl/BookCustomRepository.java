package org.kenux.miraclelibrary.repository.querydsl;

import org.kenux.miraclelibrary.domain.Book;

import java.util.List;

public interface BookCustomRepository {

    List<Book> findAllByKeyword(String keyword);
}
