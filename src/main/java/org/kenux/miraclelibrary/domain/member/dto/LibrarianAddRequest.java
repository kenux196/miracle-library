package org.kenux.miraclelibrary.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;

@NoArgsConstructor
@Getter
public class LibrarianAddRequest {

    private String name;
    private String email;
    private String password;

    @Builder
    public LibrarianAddRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .memberRole(MemberRole.LIBRARIAN)
                .build();
    }
}