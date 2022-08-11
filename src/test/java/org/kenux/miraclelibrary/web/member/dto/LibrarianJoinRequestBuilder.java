package org.kenux.miraclelibrary.web.member.dto;


import org.kenux.miraclelibrary.web.member.dto.request.LibrarianJoinRequest;

public class LibrarianJoinRequestBuilder {
    public static LibrarianJoinRequest build(String name, String email, String phone, String password) {
        return new LibrarianJoinRequest(name, email, phone, password);
    }
}