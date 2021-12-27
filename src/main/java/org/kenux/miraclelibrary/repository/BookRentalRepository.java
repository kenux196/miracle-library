package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.BookRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRentalRepository extends JpaRepository<BookRental, Long> {

    List<BookRental> findAllByMemberId(Long memberId);

    @Query("select br from BookRental br " +
            "join Book b on br.id = b.bookRental.id " +
            "where b.id = :bookId")
    List<BookRental> findAllByBook(@Param("bookId") Long bookId);
}
