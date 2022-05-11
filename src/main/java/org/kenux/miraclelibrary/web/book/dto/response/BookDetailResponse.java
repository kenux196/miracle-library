package org.kenux.miraclelibrary.web.book.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookInfo;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;

import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDetailResponse {
    private Long id;
    private String title;
    private String subTitle;
    private String author;
    private String isbn;
    private String publishDate;
    private BookCategory category;
    private String content;
    private String cover;
    private BookStatus status;

    public static BookDetailResponse from(BookInfo bookInfo) {
        return BookDetailResponse.builder()
                .id(bookInfo.getId())
                .title(bookInfo.getTitle())
                .author(bookInfo.getAuthor())
                .isbn(bookInfo.getIsbn())
                .publishDate(bookInfo.getPublishDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .category(bookInfo.getCategory())
                .content(bookInfo.getSummary())
                .cover(bookInfo.getCover())
                .subTitle(bookInfo.getSubTitle())
                .build();
    }
}