package org.kenux.miraclelibrary.domain.member.dto;

public class LibrarianJoinRequestBuilder {
    public static LibrarianJoinRequest build(String name, String email, String phone, String password) {
        return new LibrarianJoinRequest(name, email, phone, password);
    }
}