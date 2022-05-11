package org.kenux.miraclelibrary.domain.member.dto;

import org.kenux.miraclelibrary.web.member.dto.MemberJoinRequest;

public class MemberJoinRequestBuilder {
    public static MemberJoinRequest build(String name, String email, String phone, String password) {
        return new MemberJoinRequest(name, email, phone, password);
    }
}