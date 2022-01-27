package org.kenux.miraclelibrary.domain.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.domain.member.domain.MemberStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class MemberJoinRequest {

    @NotBlank
    private String name;
    @Email
    private String email;
    @NotBlank
    private String phone;

    @NotBlank
    private String password;


    MemberJoinRequest(String name, String email, String phone, String password) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
    }

    public Member toEntity() {
        Member member = Member.builder()
                .name(name)
                .email(email)
                .phone(phone)
                .memberRole(MemberRole.CUSTOMER)
                .status(MemberStatus.NORMAL)
                .build();
        member.changePassword(password);
        return member;
    }
}