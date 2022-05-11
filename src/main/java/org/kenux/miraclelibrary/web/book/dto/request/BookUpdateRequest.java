package org.kenux.miraclelibrary.web.book.dto.request;

import lombok.Data;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookInfo;
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

    public BookInfo toEntity() {
        return BookInfo.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .publishDate(LocalDate.parse(publishDate))
                .category(category)
                .summary(content)
                .cover(cover)
                .build();
    }
}