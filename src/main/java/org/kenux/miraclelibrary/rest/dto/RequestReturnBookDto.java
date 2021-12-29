package org.kenux.miraclelibrary.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestReturnBookDto {
    private Long bookId;

    private String bookTitle;
}
