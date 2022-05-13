package org.kenux.miraclelibrary.domain.book.repository;

import org.kenux.miraclelibrary.domain.book.domain.BookInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookInfoRepository extends JpaRepository<BookInfo, Long>, BookInfoCustomRepository {
}
