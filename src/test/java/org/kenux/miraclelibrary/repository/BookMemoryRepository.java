package org.kenux.miraclelibrary.repository;


import org.kenux.miraclelibrary.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookMemoryRepository implements BookRepository {
    private static BookMemoryRepository instance;

    private final List<Book> books = new ArrayList<>();

    private BookMemoryRepository() {
    }

    public static BookMemoryRepository getInstance() {
        if (instance == null) {
            instance = new BookMemoryRepository();
        }
        return instance;
    }

    @Override
    public Book save(Book book) {
        book.assignId(getNextId());
        books.add(book);
        return book;
    }

    private Long getNextId() {
        return (long) (books.size() + 1);
    }

    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public List<Book> findAllByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().equals(title))
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        books.clear();
    }
}