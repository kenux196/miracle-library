package org.kenux.miraclelibrary.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.domain.member.domain.MemberStatus;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class MemberJoinRequest {

    @NotBlank(message = "이름값이 비었습니다.")
    private String name;
    @NotBlank(message = "이메일 주소를 입력하세요")
    private String email;
    @NotBlank(message = "패스워드를 입력하세요.")
    private String password;
    
    @NotBlank(message = "전화번호를 입력하세요")
    private String phone;

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