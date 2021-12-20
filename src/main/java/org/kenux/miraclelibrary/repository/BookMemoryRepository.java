package org.kenux.miraclelibrary.repository;


import org.kenux.miraclelibrary.domain.Book;

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
        final int id = books.size() + 1;
        book.assignId(id);
        books.add(book);
        return book;
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