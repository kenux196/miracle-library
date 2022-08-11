package org.kenux.miraclelibrary.web.book.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;

@Data
@NoArgsConstructor
public class BookSearchFilter {

    private String keyword;
    private BookCategory category;

    @Builder
    public BookSearchFilter(String keyword, BookCategory category) {
        this.keyword = keyword;
        this.category = category;
    }
}