package org.kenux.miraclelibrary.repository;

import org.kenux.miraclelibrary.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);

    boolean existsByEmail(String email);
}
