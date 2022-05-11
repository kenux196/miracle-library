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
@Table(name = "book_info")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookInfo extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "title")
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @Column(name = "author")
    private String author;

    @Column(name = "cover")
    private String cover;

    @Column(name = "publish_date")
    private LocalDate publishDate;

    @Column(name = "summary")
    private String summary;

    @Column(name = "category", nullable = false)
//    @Enumerated(EnumType.STRING)
    private BookCategory category;

    @OneToMany(mappedBy = "bookInfo", cascade = CascadeType.ALL)
    private final List<Book> books = new ArrayList<>();

    @Builder
    public BookInfo(String isbn, String title, String subTitle,
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

    public void addBook(Book book) {
        books.add(book);
        if (book.getBookInfo() != null) {
            book.setBookInfo(this);
        }
    }

    public void update(BookInfo bookInfo) {
        this.isbn = bookInfo.getIsbn();
        this.title = bookInfo.getTitle();
        this.subTitle = bookInfo.getSubTitle();
        this.author = bookInfo.getAuthor();
        this.cover = bookInfo.getCover();
        this.publishDate = bookInfo.getPublishDate();
        this.summary = bookInfo.getSummary();
    }
}
