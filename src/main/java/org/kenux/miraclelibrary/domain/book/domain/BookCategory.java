package org.kenux.miraclelibrary.domain.book.domain;

import lombok.Getter;
import org.kenux.miraclelibrary.global.exception.CustomException;

import static org.kenux.miraclelibrary.global.exception.ErrorCode.PARAMETER_WRONG;

@Getter
public enum BookCategory {
    FICTION("소설"),
    ESSAY("에세이"),
    ECONOMY("경제"),
    IT("IT 기술"),
    HUMANITIES("인문");

    private final String description;

    BookCategory(String description) {
        this.description = description;
    }

    public static BookCategory getBookCategory(String category) {
        for (BookCategory bookCategory : BookCategory.values()) {
            if (bookCategory.getDescription().equals(category)) {
                return bookCategory;
            }
        }
        throw new CustomException(PARAMETER_WRONG);
    }
}
