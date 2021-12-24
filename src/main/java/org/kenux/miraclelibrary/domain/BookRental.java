package org.kenux.miraclelibrary.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "book_rental")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookRental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDate rentalStartDate;

    @Setter
    private LocalDate returnDate;

    @Builder
    public BookRental(Book book, Member member, LocalDate rentalStartDate) {
        this.book = book;
        this.member = member;
        this.rentalStartDate = rentalStartDate;
    }

    public LocalDate getRentalEndDate() {
        return rentalStartDate.plusWeeks(2);
    }

    public boolean isOverDue(LocalDate today) {
        return today.isAfter(rentalStartDate.plusWeeks(2));
    }
}
