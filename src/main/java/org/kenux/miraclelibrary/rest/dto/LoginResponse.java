package org.kenux.miraclelibrary.rest.dto;

import lombok.Builder;
import lombok.Data;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.domain.enums.MemberRole;

@Data
public class LoginResponse {

    private Long id;
    private String name;
    private String email;
    private MemberRole role;

    @Builder
    public LoginResponse(Long id, String name, String email, MemberRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public static LoginResponse of(Member member) {
        return LoginResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .role(member.getMemberRole())
                .build();
    }
}