package org.kenux.miraclelibrary.repository;


import org.kenux.miraclelibrary.domain.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookMemoryRepository {
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

    public Book save(Book book) {
        book.assignId(getNextId());
        books.add(book);
        return book;
    }

    private Long getNextId() {
        return (long) (books.size() + 1);
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