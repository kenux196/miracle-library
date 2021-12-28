package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.BookRentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRentInfoRepository extends JpaRepository<BookRentInfo, Long> {

    List<BookRentInfo> findAllByMemberId(Long memberId);

    List<BookRentInfo> findAllByBookId(Long bookId);
}
