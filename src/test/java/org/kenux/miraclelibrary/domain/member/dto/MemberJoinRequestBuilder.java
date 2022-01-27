package org.kenux.miraclelibrary.domain.member.dto;

public class MemberJoinRequestBuilder {
    public static MemberJoinRequest build(String name, String email, String phone, String password) {
        return new MemberJoinRequest(name, email, phone, password);
    }
}