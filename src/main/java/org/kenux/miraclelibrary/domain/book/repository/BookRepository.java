package org.kenux.miraclelibrary.domain.book.repository;

import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByStatus(BookStatus bookStatus);
}
