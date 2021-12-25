package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.BookRental;
import org.kenux.miraclelibrary.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRentalRepository extends JpaRepository<BookRental, Long> {

    List<BookRental> findAllByMember(Member member);

    List<BookRental> findAllByBook(Book book);
}
