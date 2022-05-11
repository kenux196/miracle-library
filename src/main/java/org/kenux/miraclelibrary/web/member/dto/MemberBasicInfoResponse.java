package org.kenux.miraclelibrary.web.member.dto;

import lombok.Builder;
import lombok.Getter;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.domain.member.domain.MemberStatus;

@Getter
@Builder
public class MemberBasicInfoResponse {

    private Long id;
    private String name;
    private String email;
    private MemberRole role;
    private MemberStatus status;

    public static MemberBasicInfoResponse from(Member member) {
        return MemberBasicInfoResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .role(member.getRole())
                .status(member.getStatus())
                .build();
    }
}
