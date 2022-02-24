package me.kenux.springsecurity.web.dto;

import lombok.Data;
import me.kenux.springsecurity.domain.book.Book;

@Data
public class BookAddRequest {

    private String title;
    private String author;
    private Long price;
    private Long quantity;

    public Book toEntity() {
        return new Book(title, author, price, quantity);
    }
}