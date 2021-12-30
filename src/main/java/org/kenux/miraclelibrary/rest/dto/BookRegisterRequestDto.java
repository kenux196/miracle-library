package org.kenux.miraclelibrary.rest.dto;

import lombok.Builder;
import lombok.Data;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.enums.BookStatus;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@Builder
public class BookRegisterRequestDto {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .createDate(LocalDateTime.now())
                .status(BookStatus.RENTABLE)
                .build();
    }
}