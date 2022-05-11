package org.kenux.miraclelibrary.domain.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.domain.member.repository.filter.MemberFindFilter;

import java.util.List;

import static org.kenux.miraclelibrary.domain.member.domain.QMember.member;


@RequiredArgsConstructor
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Member> findCustomerByFilter(MemberFindFilter filter) {
        return jpaQueryFactory.selectFrom(member)
                .where(onlyCustomer(), equalId(filter), equalName(filter), equalEmail(filter), equalStatus(filter))
                .fetch();
    }

    private BooleanExpression onlyCustomer() {
        return member.role.eq(MemberRole.CUSTOMER);
    }

    private BooleanExpression equalId(MemberFindFilter filter) {
        if (filter.getId() == null) {
            return null;
        }
        return member.id.eq(filter.getId());
    }

    private BooleanExpression equalName(MemberFindFilter filter) {
        if (filter.getName() == null) {
            return null;
        }
        return member.name.eq(filter.getName());
    }

    private BooleanExpression equalEmail(MemberFindFilter filter) {
        if (filter.getEmail() == null) {
            return null;
        }
        return member.email.eq(filter.getEmail());
    }

    private BooleanExpression equalStatus(MemberFindFilter filter) {
        if (filter.getStatus() == null) {
            return null;
        }
        return member.status.eq(filter.getStatus());
    }
}
