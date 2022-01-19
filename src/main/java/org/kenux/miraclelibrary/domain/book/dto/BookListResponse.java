package org.kenux.miraclelibrary.domain.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;

@Data
@Builder
@AllArgsConstructor
public class BookListResponse {
    private Long bookId;
    private String title;
    private String author;
    private String isbn;
    private BookStatus status;

    public static BookListResponse of(Book book) {
        return BookListResponse.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .status(book.getStatus())
                .build();
    }
}