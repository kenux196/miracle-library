package org.kenux.miraclelibrary.domain.book.controller.response;

import lombok.Builder;
import lombok.Data;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;

import java.time.LocalDate;

@Data
@Builder
public class BookDetailResponse {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private LocalDate publicationDate;
    private BookCategory category;
    private String content;
    private String cover;
    private BookStatus status;

    public static BookDetailResponse from(Book book) {
        return BookDetailResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publicationDate(book.getPublishDate())
                .category(book.getCategory())
                .content(book.getContent())
                .cover(book.getCover())
                .status(book.getStatus())
                .build();
    }
}