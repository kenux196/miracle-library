package org.kenux.miraclelibrary.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.domain.member.domain.MemberStatus;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class LibrarianAddRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phone;

    @Builder
    public LibrarianAddRequest(String name, String email, String password, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Member toEntity() {
        Member member = Member.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .memberRole(MemberRole.LIBRARIAN)
                .status(MemberStatus.NORMAL)
                .build();
        member.changePassword(password);
        return member;
    }
}