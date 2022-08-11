package org.kenux.miraclelibrary.web.book.dto.response;

import lombok.Data;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;

@Data
public class BookItemResponse {
    private Long bookId;
    private BookStatus status;

    public BookItemResponse(Long bookId, BookStatus status) {
        this.bookId = bookId;
        this.status = status;
    }
}
