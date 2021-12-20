package org.kenux.miraclelibrary.repository;


import org.kenux.miraclelibrary.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookRepository {
    private static BookRepository instance;

    private final List<Book> books = new ArrayList<>();

    private BookRepository() {
    }

    public static BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepository();
        }
        return instance;
    }

    public Book save(Book book) {
        final int id = books.size() + 1;
        book.assignId(id);
        books.add(book);
        return book;
    }

    public List<Book> findAll() {
        return books;
    }

    public List<Book> findAllByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    public void clear() {
        books.clear();
    }
}