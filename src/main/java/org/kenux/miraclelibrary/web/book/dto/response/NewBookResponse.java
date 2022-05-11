package org.kenux.miraclelibrary.web.book.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookInfo;

@Data
@Builder
@AllArgsConstructor
public class NewBookResponse {
    private Long bookId;
    private String title;
    private String author;
    private BookCategory category;
    private String cover;

    public static NewBookResponse from(BookInfo book) {
        return NewBookResponse.builder()
                .bookId(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .cover(book.getCover())
                .category(book.getCategory())
                .build();
    }
}