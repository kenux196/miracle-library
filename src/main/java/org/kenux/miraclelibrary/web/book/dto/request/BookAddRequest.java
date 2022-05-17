package org.kenux.miraclelibrary.web.book.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.book.domain.BookCategory;
import org.kenux.miraclelibrary.domain.book.domain.BookInfo;
import org.kenux.miraclelibrary.domain.book.domain.BookItem;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BookAddRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    @NotBlank
    private String isbn;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String publishDate;

    @NotNull
    private BookCategory category;

    @NotNull
    private Integer amount;

    public BookInfo toEntity() {
        BookInfo bookInfo = BookInfo.builder()
                .title(title)
                .author(author)
                .isbn(isbn)
                .publishDate(LocalDate.parse(publishDate))
                .category(category)
                .build();

        if (amount != null) {
            addBook(bookInfo, amount);
        }
        return bookInfo;
    }

    private void addBook(BookInfo bookInfo, int count) {
        for (int i = 0; i < count; i++) {
            bookInfo.addBook(BookItem.createNewBook());
        }
    }
}