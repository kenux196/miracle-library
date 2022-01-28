package org.kenux.miraclelibrary.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.domain.member.domain.MemberStatus;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberFindFilter {

    private Long id;
    private String name;
    private String email;
    private MemberRole role;
    private MemberStatus status;
}
