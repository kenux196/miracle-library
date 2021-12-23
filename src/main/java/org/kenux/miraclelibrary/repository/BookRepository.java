package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.repository.querydsl.BookCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookCustomRepository {

    @Query("select b from Book b where b.title like %:title%")
    Optional<Book> findByTitle(@Param("title") String title);

    Optional<Book> findByAuthor(String author);

    Optional<Book> findByIsbn(String isbn);
}
