package org.kenux.miraclelibrary.domain.member.dto;

public class LibrarianAddRequestBuilder {
    public static LibrarianAddRequest build(String name, String email, String phone, String password) {
        return new LibrarianAddRequest(name, email, phone, password);
    }
}