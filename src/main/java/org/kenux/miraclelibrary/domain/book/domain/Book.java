package org.kenux.miraclelibrary.domain.book.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.base.BaseTimeEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "cover")
    private String cover;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "summary")
    private String summary;

    @Column(name = "category", nullable = false)
//    @Enumerated(EnumType.STRING)
    private BookCategory category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private final List<BookItem> bookItems = new ArrayList<>();

    @Builder
    public Book(String isbn, String title, String subTitle,
                String author, String cover, LocalDate publishDate, String summary, BookCategory category) {
        this.isbn = isbn;
        this.title = title;
        this.subTitle = subTitle;
        this.author = author;
        this.cover = cover;
        this.publishDate = publishDate;
        this.summary = summary;
        this.category = category;
    }

    public void update(Book book) {
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.subTitle = book.getSubTitle();
        this.author = book.getAuthor();
        this.cover = book.getCover();
        this.publishDate = book.getPublishDate();
        this.summary = book.getSummary();
    }

    public void addBook(BookItem bookItem) {
        bookItems.add(bookItem);
        if (bookItem.getBook() != this) {
            bookItem.setBook(this);
        }
    }
}
