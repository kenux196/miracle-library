package org.kenux.miraclelibrary.domain;

import lombok.*;

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
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Setter
    private LocalDateTime returnDate;

    @Builder
    public BookRentInfo(Member member, Book book, LocalDateTime startDate) {
        this.member = member;
        this.book = book;
        this.startDate = startDate;
        this.endDate = startDate.plusWeeks(2);
    }

    public LocalDate getEndDate() {
        return endDate.toLocalDate();
    }

    public boolean isOverDue(LocalDate today) {
        return today.isAfter(getEndDate());
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