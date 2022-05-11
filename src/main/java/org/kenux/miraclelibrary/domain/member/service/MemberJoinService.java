package org.kenux.miraclelibrary.domain.member.service;

import org.kenux.miraclelibrary.web.member.dto.request.MemberJoinRequest;

public interface MemberJoinService {
    Long join(MemberJoinRequest memberJoinRequest);
}
