package org.kenux.miraclelibrary.web.book.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookInfo;
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

    public static BookResponse from(BookInfo bookInfo) {
        return BookResponse.builder()
                .bookId(bookInfo.getId())
                .title(bookInfo.getTitle())
                .author(bookInfo.getAuthor())
//                .status(bookInfo.getStatus())
                // TODO : modify   - sky 2022/05/12
                .cover(bookInfo.getCover())
                .category(bookInfo.getCategory())
                .build();
    }
}