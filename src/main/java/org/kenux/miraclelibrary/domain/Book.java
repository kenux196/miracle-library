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

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void rented() {
        amount--;
    }

    public void returned() {
        amount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (!id.equals(book.id)) return false;
        if (!title.equals(book.title)) return false;
        if (!author.equals(book.author)) return false;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + isbn.hashCode();
        return result;
    }
}
