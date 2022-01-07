package org.kenux.miraclelibrary.rest.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.domain.enums.MemberRole;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Getter
public class MemberJoinRequest {

    @NotBlank(message = "이름을 입력하세요")
    private String name;
    @NotBlank(message = "이메일 주소를 입력하세요")
    private String email;
    @NotBlank(message = "패스워드를 입력하세요.")
    private String password;

    @Builder
    public MemberJoinRequest(String name, String email, String password) {
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