package org.kenux.miraclelibrary.domain;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.enums.BookStatus;

import javax.persistence.*;


@Entity
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "amount")
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookStatus status;

    @Builder
    public Book(String title, String author, String isbn, Integer amount) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.amount = amount;
    }

    public boolean isAvailableForRental() {
        return amount > 0;
    }

    public void changeStatus(BookStatus status) {
        this.status = status;
    }

    public void assignId(Long id) {
        this.id = id;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void rented() {
        amount--;
    }

    public void returned() {
        amount++;
    }
}
