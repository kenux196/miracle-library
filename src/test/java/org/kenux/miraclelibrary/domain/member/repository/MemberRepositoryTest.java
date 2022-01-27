package org.kenux.miraclelibrary.domain.member.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.global.config.JpaAuditingConfig;
import org.kenux.miraclelibrary.global.config.QueryDslConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({QueryDslConfig.class, JpaAuditingConfig.class})
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    private Member member;
    private Member librarian;

    @BeforeEach
    void setup() {
        member = createMember();
        librarian = createLibrarian();
        memberRepository.save(member);
        memberRepository.save(librarian);
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("회원 저장: 기본 저장")
    void save() {
        // when
        final Optional<Member> result = memberRepository.findById(member.getId());

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("동일한 이메일이 존재하는지 조회")
    void existByEmail() {
        boolean result = memberRepository.existsByEmail(member.getEmail());
        assertThat(result).isTrue();

        result = memberRepository.existsByEmail("test1@test.com");
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("전체 멤버 조회")
    void findAll() {
        List<Member> members = memberRepository.findAll();
        assertThat(members).hasSize(2);
    }

    @Test
    @DisplayName("findById: id로 조회")
    void findById() {
        Optional<Member> found = memberRepository.findById(member.getId());
        assertThat(found).isNotEmpty();
        assertThat(found.get().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("findByName: 이름으로 조회")
    void findByName() {
        Optional<Member> found = memberRepository.findByName(member.getName());
        assertThat(found).isNotEmpty();
        assertThat(found.get().getName()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("findByEmail: 이메일로 조회")
    void findByEmail() throws Exception {
        Optional<Member> found = memberRepository.findByEmail(member.getEmail());
        assertThat(found).isNotEmpty();
        assertThat(found.get().getEmail()).isEqualTo(member.getEmail());
    }

    private Member createMember() {
        return Member.createCustomer(
                "member1", "member1@test.com", "010-1234-5678", "password");
    }

    private Member createLibrarian() {
        return Member.createLibrarian(
                "librarian", "librarian@test.com", "010-1234-5678", "password");
    }
}