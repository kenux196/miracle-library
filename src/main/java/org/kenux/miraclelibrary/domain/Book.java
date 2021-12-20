package org.kenux.miraclelibrary.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.enums.BookStatus;


@NoArgsConstructor
@Getter
public class Book {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private BookStatus status;

    @Builder
    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public Long getId() {
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

    public void assignId(Long id) {
        this.id = id;
    }
}
