package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.Book;

import java.util.List;

public interface BookRepository {

    Book save(Book book);

    List<Book> findAll();

    List<Book> findAllByTitle(String title);

    void clear();
}
