package org.kenux.miraclelibrary.domain.bookrent.domain;

import lombok.*;
import org.kenux.miraclelibrary.domain.book.domain.BookItem;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.kenux.miraclelibrary.domain.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_rental")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookRentInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "book_item_id", nullable = false)
    private BookItem bookItem;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Setter
    private LocalDateTime returnDate;

    @Builder
    public BookRentInfo(Member member, BookItem bookItem, LocalDateTime startDate) {
        this.member = member;
        this.bookItem = bookItem;
        this.startDate = startDate;
        this.endDate = startDate.plusWeeks(2);
    }

    public LocalDate getEndDate() {
        return endDate.toLocalDate();
    }

    public boolean isOverDue(LocalDate today) {
        return today.isAfter(getEndDate());
    }

    public static BookRentInfo rent(Member member, BookItem bookItem) {
        bookItem.changeBookStatus(BookStatus.RENTED);
        return BookRentInfo.builder()
                .member(member)
                .bookItem(bookItem)
                .startDate(LocalDateTime.now())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookRentInfo that = (BookRentInfo) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
