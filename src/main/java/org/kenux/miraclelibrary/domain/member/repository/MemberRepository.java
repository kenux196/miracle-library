package org.kenux.miraclelibrary.domain.member.repository;

import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Optional<Member> findByName(String name);

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

}
