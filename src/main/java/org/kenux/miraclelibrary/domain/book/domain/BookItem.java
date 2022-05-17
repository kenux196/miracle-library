package org.kenux.miraclelibrary.domain.book.domain;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.base.BaseTimeEntity;

import javax.persistence.*;

import static org.kenux.miraclelibrary.domain.book.domain.BookStatus.RENTABLE;
import static org.kenux.miraclelibrary.domain.book.domain.BookStatus.RENTED;


@Entity
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookItem extends BaseTimeEntity {

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
//        if (this.bookInfo != null) {
//            this.bookInfo.getBooks().remove(this);
//        }
        this.bookInfo = bookInfo;
//        bookInfo.getBooks().add(this);
    }

    public boolean isHeldBook() {
        return status.equals(RENTABLE) || status.equals(RENTED);
    }

    public void changeBookStatus(BookStatus status) {
        this.status = status;
    }

    public static BookItem createNewBook() {
        BookItem bookItem = new BookItem();
        bookItem.changeBookStatus(RENTABLE);
        return bookItem;
    }
}
