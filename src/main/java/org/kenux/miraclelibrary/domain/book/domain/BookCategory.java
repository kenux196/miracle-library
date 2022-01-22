package org.kenux.miraclelibrary.domain.book.domain;

import lombok.Getter;

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
}
