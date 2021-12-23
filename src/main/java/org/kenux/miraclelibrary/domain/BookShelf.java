package org.kenux.miraclelibrary.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "member_bookshelf")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookShelf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany
    private List<Book> bookList = new ArrayList<>();

    public void addBook(Book book) {
        bookList.add(book);
    }

    public void removeBook(Book book) {
        bookList.remove(book);
    }

    public List<Book> findBook(Book book) {
        return bookList.stream()
                .filter(book1 -> book1.getTitle().equals(book.getTitle()))
                .collect(Collectors.toList());
    }
}
