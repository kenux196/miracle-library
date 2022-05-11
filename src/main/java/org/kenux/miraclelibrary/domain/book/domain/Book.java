package org.kenux.miraclelibrary.domain.book.domain;


import lombok.*;
import org.kenux.miraclelibrary.domain.base.BaseTimeEntity;

import javax.persistence.*;

import static org.kenux.miraclelibrary.domain.book.domain.BookStatus.RENTABLE;
import static org.kenux.miraclelibrary.domain.book.domain.BookStatus.RENTED;


@Entity
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_info_id")
    private BookInfo bookInfo;

    public void setBookInfo(BookInfo bookInfo) {
        if (this.bookInfo != null) {
            this.bookInfo.getBooks().remove(this);
        }
        this.bookInfo = bookInfo;
    }

    public boolean isHeldBook() {
        return status.equals(RENTABLE) || status.equals(RENTED);
    }

    public void changeBookStatus(BookStatus status) {
        this.status = status;
    }

    public static Book createBook() {
        Book book = new Book();
        book.changeBookStatus(RENTABLE);
        return book;
    }
}
