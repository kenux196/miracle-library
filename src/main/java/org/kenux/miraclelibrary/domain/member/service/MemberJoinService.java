package org.kenux.miraclelibrary.domain.member.service;

import org.kenux.miraclelibrary.domain.member.dto.MemberJoinRequest;

public interface MemberJoinService {
    Long join(MemberJoinRequest memberJoinRequest);
}
