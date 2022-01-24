package org.kenux.miraclelibrary.domain.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;

import java.time.LocalDate;

@Data
public class BookUpdateRequest {

    private Long id;
    private String title;
    private String author;
    private String isbn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate publicationDate;
    private BookCategory category;
    private String content;
    private String cover;
}