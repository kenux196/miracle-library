package org.kenux.miraclelibrary.domain.bookrent.repository;

import org.kenux.miraclelibrary.domain.bookrent.domain.BookRentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRentInfoRepository extends JpaRepository<BookRentInfo, Long> {

    List<BookRentInfo> findAllByMemberId(Long memberId);

    List<BookRentInfo> findAllByBookItemId(Long bookId);

    @Query("select bri from BookRentInfo bri " +
            "where bri.bookItem.id in :bookIds")
    List<BookRentInfo> findAllByBookItemIds(@Param("bookIds") List<Long> bookIds);
}
