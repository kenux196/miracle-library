package org.kenux.miraclelibrary.domain.book.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class BookUpdateRequest {

    private Long id;
    private String title;
    private String author;
    private String isbn;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String publishDate;
    private BookCategory category;
    private String content;
    private String cover;

    public Book toEntity() {
        return Book.builder()
                .id(id)
                .title(title)
                .author(author)
                .isbn(isbn)
                .publishDate(LocalDate.parse(publishDate))
                .category(category)
                .content(content)
                .cover(cover)
                .build();
    }
}