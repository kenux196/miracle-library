package org.kenux.miraclelibrary.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.Book;
import org.kenux.miraclelibrary.domain.enums.BookStatus;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookRegisterRequest {

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