package org.kenux.miraclelibrary.domain;


import org.kenux.miraclelibrary.domain.enums.BookStatus;

public class Book {

    private final String title;
    private final String author;
    private final String isbn;

    private int id = 0;
    private BookStatus status;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public BookStatus getStatus() {
        return status;
    }

    public boolean isAvailableForRental() {
        return status == BookStatus.AVAILABLE_FOR_RENTAL;
    }

    public void changeStatus(BookStatus status) {
        this.status = status;
    }

    public void assignId(int id) {
        this.id = id;
    }
}
