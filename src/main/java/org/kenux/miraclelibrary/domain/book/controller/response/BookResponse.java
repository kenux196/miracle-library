package org.kenux.miraclelibrary.domain.book.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long bookId;
    private String title;
    private String author;
    private BookCategory category;
    private BookStatus status;
    private String cover;

    public static BookResponse from(Book book) {
        return BookResponse.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .status(book.getStatus())
                .cover(book.getCover())
                .category(book.getCategory())
                .build();
    }
}