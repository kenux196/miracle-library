package org.kenux.miraclelibrary.web.book.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookInfo;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

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
    private List<BookStatusResponse> bookStatus;

    public static BookDetailResponse from(BookInfo bookInfo) {
        List<BookStatusResponse> bookStatusResponses = bookInfo.getBooks().stream()
                .map(book -> new BookStatusResponse(book.getId(), book.getStatus().name()))
                .collect(Collectors.toList());


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
                .bookStatus(bookStatusResponses)
                .build();
    }
}