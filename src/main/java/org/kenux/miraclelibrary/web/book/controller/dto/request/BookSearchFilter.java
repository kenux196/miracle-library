package org.kenux.miraclelibrary.web.book.controller.dto.request;

import lombok.Builder;
import lombok.Data;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;

@Data
public class BookSearchFilter {

    private String keyword;
    private BookCategory category;

    @Builder
    public BookSearchFilter(String keyword, BookCategory category) {
        this.keyword = keyword;
        this.category = category;
    }
}