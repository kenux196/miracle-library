package org.kenux.miraclelibrary.web.book.dto.response;

import lombok.Data;

@Data
public class BookItemResponse {
    private Long bookId;
    private String status;

    public BookItemResponse(Long bookId, String status) {
        this.bookId = bookId;
        this.status = status;
    }
}
