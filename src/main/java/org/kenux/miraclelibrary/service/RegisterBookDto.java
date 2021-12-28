package org.kenux.miraclelibrary.service;

import lombok.Builder;
import lombok.Data;
import org.kenux.miraclelibrary.domain.Book;

@Data
@Builder
public class RegisterBookDto {

    private String title;
    private String author;
    private String isbn;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .build();
    }
}