package org.kenux.miraclelibrary.domain.book.domain;

import lombok.Getter;

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
}
