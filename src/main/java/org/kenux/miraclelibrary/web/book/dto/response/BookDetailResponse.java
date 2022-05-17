package org.kenux.miraclelibrary.web.book.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;

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

    public static BookDetailResponse from(Book book) {
        List<BookStatusResponse> bookStatusResponses = book.getBookItems().stream()
                .map(bookItem -> new BookStatusResponse(bookItem.getId(), bookItem.getStatus().name()))
                .collect(Collectors.toList());


        return BookDetailResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .publishDate(book.getPublishDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .category(book.getCategory())
                .content(book.getSummary())
                .cover(book.getCover())
                .subTitle(book.getSubTitle())
                .bookStatus(bookStatusResponses)
                .build();
    }
}