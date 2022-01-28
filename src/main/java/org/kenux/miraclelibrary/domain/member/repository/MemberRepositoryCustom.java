package org.kenux.miraclelibrary.domain.member.repository;

import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.dto.MemberFindFilter;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findCustomerByFilter(MemberFindFilter filter);
}
