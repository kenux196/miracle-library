package org.kenux.miraclelibrary.domain.member.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.domain.member.domain.MemberStatus;
import org.kenux.miraclelibrary.domain.member.dto.MemberFindFilter;
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

    private Member memberNormal;
    private Member memberDormancy;
    private Member librarian;

    @BeforeEach
    void setup() {
        memberNormal = Member.createCustomer(
                "member1", "member1@test.com", "010-1234-5678", "password");
        memberDormancy = Member.createCustomer(
                "member2", "member2@test.com", "010-1234-5678", "password");
        memberDormancy.changeStatus(MemberStatus.DORMANCY);
        librarian = createLibrarian();
        memberRepository.save(memberNormal);
        memberRepository.save(memberDormancy);
        memberRepository.save(librarian);
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("회원 저장: 기본 저장")
    void save() {
        // when
        final Optional<Member> result = memberRepository.findById(memberNormal.getId());

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(memberNormal.getId());
    }

    @Test
    @DisplayName("동일한 이메일이 존재하는지 조회")
    void existByEmail() {
        boolean result = memberRepository.existsByEmail(memberNormal.getEmail());
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
        Optional<Member> found = memberRepository.findById(memberNormal.getId());
        assertThat(found).isNotEmpty();
        assertThat(found.get().getId()).isEqualTo(memberNormal.getId());
    }

    @Test
    @DisplayName("findByName: 이름으로 조회")
    void findByName() {
        Optional<Member> found = memberRepository.findByName(memberNormal.getName());
        assertThat(found).isNotEmpty();
        assertThat(found.get().getName()).isEqualTo(memberNormal.getName());
    }

    @Test
    @DisplayName("findByEmail: 이메일로 조회")
    void findByEmail() throws Exception {
        Optional<Member> found = memberRepository.findByEmail(memberNormal.getEmail());
        assertThat(found).isNotEmpty();
        assertThat(found.get().getEmail()).isEqualTo(memberNormal.getEmail());
    }

    @Test
    @DisplayName("findCustomerByFilter: 전체 멤버에서 고객만 조회한다.")
    void findCustomerByFilter() throws Exception {
        // given
        MemberFindFilter filter = new MemberFindFilter();

        // when
        List<Member> members = memberRepository.findCustomerByFilter(filter);

        // then
        assertThat(members).hasSize(2);
        assertThat(members.get(0).getRole()).isEqualTo(MemberRole.CUSTOMER);
    }

    @Test
    void findCustomerByFilter_id값만_포함() throws Exception {
        // given
        MemberFindFilter filter = new MemberFindFilter();
        filter.setId(memberNormal.getId());

        // when
        List<Member> members = memberRepository.findCustomerByFilter(filter);

        // then
        assertThat(members).hasSize(1);
        assertThat(members.get(0).getId()).isEqualTo(memberNormal.getId());
    }

    @Test
    void findCustomerByFilter_이름만_포함() throws Exception {
        // given
        MemberFindFilter filter = new MemberFindFilter();
        filter.setName(memberNormal.getName());

        // when
        List<Member> members = memberRepository.findCustomerByFilter(filter);

        // then
        assertThat(members).hasSize(1);
        assertThat(members.get(0).getId()).isEqualTo(memberNormal.getId());
    }

    @Test
    void findCustomerByFilter_이메일만_포함() throws Exception {
        // given
        MemberFindFilter filter = new MemberFindFilter();
        filter.setEmail(memberNormal.getEmail());

        // when
        List<Member> members = memberRepository.findCustomerByFilter(filter);

        // then
        assertThat(members).hasSize(1);
        assertThat(members.get(0).getEmail()).isEqualTo(memberNormal.getEmail());
    }

    @Test
    void findCustomerByFilter_고객상태만_포함() throws Exception {
        // given
        MemberFindFilter filter = new MemberFindFilter();
        filter.setStatus(memberNormal.getStatus());

        // when
        List<Member> members = memberRepository.findCustomerByFilter(filter);

        // then
        assertThat(members).hasSize(1);
        assertThat(members.get(0).getStatus()).isEqualTo(memberNormal.getStatus());
    }

    @Test
    void findCustomerByFilter_이름과이메일_포함() throws Exception {
        // given
        MemberFindFilter filter = new MemberFindFilter();
        filter.setName(memberNormal.getName());
        filter.setEmail(memberNormal.getEmail());

        // when
        List<Member> members = memberRepository.findCustomerByFilter(filter);

        // then
        assertThat(members).hasSize(1);
        assertThat(members.get(0).getEmail()).isEqualTo(memberNormal.getEmail());
        assertThat(members.get(0).getName()).isEqualTo(memberNormal.getName());
    }

    @Test
    void findCustomerByFilter_이름과이메일_불일치_검색결과없음() throws Exception {
        // given
        MemberFindFilter filter = new MemberFindFilter();
        filter.setName(memberNormal.getName());
        filter.setEmail("notUser@test.com");

        // when
        List<Member> members = memberRepository.findCustomerByFilter(filter);

        // then
        assertThat(members).isEmpty();
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