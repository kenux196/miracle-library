package org.kenux.miraclelibrary.domain.book.domain;

public enum BookStatus {
    RENTABLE("대여가능"),
    RENTED("대여중"),
    LOST("분실"),
    REMOVED("파기");

    private String value;

    BookStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
