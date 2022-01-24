package org.kenux.miraclelibrary.domain.book.domain;

import lombok.Getter;
import org.kenux.miraclelibrary.global.exception.CustomException;

import static org.kenux.miraclelibrary.global.exception.ErrorCode.PARAMETER_WRONG;

@Getter
public enum BookCategory {
    FICTION("fiction"),
    ESSAY("essay"),
    ECONOMY("economy"),
    IT("it"),
    HUMANITIES("humanities");

    private final String value;

    BookCategory(String value) {
        this.value = value;
    }

    public static BookCategory getBookCategory(String category) {
        for (BookCategory bookCategory : BookCategory.values()) {
            if (bookCategory.getValue().equals(category)) {
                return bookCategory;
            }
        }
        throw new CustomException(PARAMETER_WRONG);
    }
}
