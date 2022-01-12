package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.enums.BookStatus;
import org.kenux.miraclelibrary.repository.querydsl.BookCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long>, BookCustomRepository {
    int countByStatus(BookStatus rented);
}
