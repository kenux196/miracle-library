package org.kenux.miraclelibrary.web.member.dto;

import org.kenux.miraclelibrary.web.member.dto.request.MemberJoinRequest;

public class MemberJoinRequestBuilder {
    public static MemberJoinRequest build(String name, String email, String phone, String password) {
        return new MemberJoinRequest(name, email, phone, password);
    }
}