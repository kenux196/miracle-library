package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.BookRental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRentalRepository extends JpaRepository<BookRental, Long> {

    List<BookRental> findAllByMemberId(Long memberId);

    List<BookRental> findAllByBookId(Long bookId);
}
