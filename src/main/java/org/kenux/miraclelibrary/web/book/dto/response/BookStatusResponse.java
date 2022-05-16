package org.kenux.miraclelibrary.web.book.dto.response;

import lombok.Data;

@Data
public class BookStatusResponse {
    private Long bookId;
    private String status;

    public BookStatusResponse(Long bookId, String status) {
        this.bookId = bookId;
        this.status = status;
    }
}
