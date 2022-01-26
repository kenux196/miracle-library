package org.kenux.miraclelibrary.domain.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
public class BookResponse {
    private Long bookId;
    private String title;
    private String author;
    private String isbn;
    private LocalDate publicationDate;
    private BookStatus status;

    public static BookResponse from(Book book) {
        return BookResponse.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publicationDate(book.getPublicationDate())
                .status(book.getStatus())
                .build();
    }
}