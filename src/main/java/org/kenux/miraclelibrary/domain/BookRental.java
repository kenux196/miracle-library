package org.kenux.miraclelibrary.domain;

import lombok.*;
import org.kenux.miraclelibrary.domain.enums.BookStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book_rental")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookRental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "bookRental")
    private List<Book> books = new ArrayList<>();

    private LocalDateTime rentalStartDate;

    @Setter
    private LocalDateTime returnDate;

    @Builder
    public BookRental(Member member, List<Book> books, LocalDateTime rentalStartDate) {
        this.member = member;
        this.rentalStartDate = rentalStartDate;
        addBooks(books);
    }

    public void addBooks(List<Book> books) {
        books.forEach(book -> {
            book.changeStatus(BookStatus.RENTED);
            book.changeBookRental(this);
        });
    }

    public LocalDate getRentalEndDate() {
        return rentalStartDate.plusWeeks(2).toLocalDate();
    }

    public boolean isOverDue(LocalDate today) {
        return today.isAfter(rentalStartDate.plusWeeks(2).toLocalDate());
    }
}
