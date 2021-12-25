package org.kenux.miraclelibrary.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RequestBookReturn {
    private Long bookId;

    private String bookTitle;
}
