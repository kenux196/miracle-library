package org.kenux.miraclelibrary.domain.book.dto;

import lombok.Data;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;

@Data
public class BookSearchFilter {

    private String keyword;
    private BookCategory category;

}