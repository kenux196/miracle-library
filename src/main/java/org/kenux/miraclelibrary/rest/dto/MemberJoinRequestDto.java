package org.kenux.miraclelibrary.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.domain.enums.MemberRole;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class MemberJoinRequestDto {

    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    @Builder
    public MemberJoinRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .password(password)
                .memberRole(MemberRole.CUSTOMER)
                .build();
    }
}