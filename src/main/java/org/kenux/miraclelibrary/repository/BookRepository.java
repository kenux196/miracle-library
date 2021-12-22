package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByTitle(String title);
}
