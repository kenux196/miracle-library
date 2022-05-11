package org.kenux.miraclelibrary.domain.member.dto;


import org.kenux.miraclelibrary.web.member.dto.LibrarianJoinRequest;

public class LibrarianJoinRequestBuilder {
    public static LibrarianJoinRequest build(String name, String email, String phone, String password) {
        return new LibrarianJoinRequest(name, email, phone, password);
    }
}