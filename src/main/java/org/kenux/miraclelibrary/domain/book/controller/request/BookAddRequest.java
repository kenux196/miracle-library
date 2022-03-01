package org.kenux.miraclelibrary.domain.book.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.kenux.miraclelibrary.domain.book.domain.Book;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookStatus;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class BookAddRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate publishDate;

    @NotNull
    private BookCategory category;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .publishDate(publishDate)
                .category(category)
                .status(BookStatus.RENTABLE)
                .build();
    }
}