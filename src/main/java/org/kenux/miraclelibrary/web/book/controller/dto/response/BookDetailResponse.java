package org.kenux.miraclelibrary.web.book.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailResponse {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String publishDate;
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
                .publishDate(book.getPublishDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .category(book.getCategory())
                .content(book.getContent())
                .cover(book.getCover())
                .status(book.getStatus())
                .build();
    }
}