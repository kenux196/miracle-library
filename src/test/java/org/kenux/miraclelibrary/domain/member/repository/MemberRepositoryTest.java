package org.kenux.miraclelibrary.domain.member.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.domain.MemberRole;
import org.kenux.miraclelibrary.global.config.JpaAuditingConfig;
import org.kenux.miraclelibrary.global.config.QueryDslConfig;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.kenux.miraclelibrary.global.exception.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
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
    }

    @Test
    @DisplayName("고객 정보 저장 테스트")
    void test_saveCustomer() {
        Member saved = memberRepository.save(member);

        assertThat(member.getId()).isNotNull();
        assertThat(saved.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("멤버 저장 시, 생성일 기록")
    void test_savedMemberHasCreateDate() {
        Member saved = memberRepository.save(member);

        assertThat(saved.getCreateDate()).isNotNull();
        assertThat(saved.getCreateDate()).isAfter(LocalDateTime.of(2022, 1, 1, 1,1,1));
    }
    
    @Test
    @DisplayName("멤버 정보 변경, UpdateDate 갱신")
    void test_updateDate() {
        // given
        Member saved = memberRepository.save(member);

        // when
        saved.changeEmail("kenux@test.com");
        Long id = saved.getId();
        em.flush();
        em.clear();

        final Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        // then
        assertThat(findMember.getUpdateDate()).isAfter(findMember.getCreateDate());
    }

    @Test
    @DisplayName("고객 이메일이 존재하는지 검사한다.")
    void test_existEmail() {
        memberRepository.save(member);

        boolean result = memberRepository.existsByEmail("customer1@test.com");
        assertThat(result).isTrue();

        result = memberRepository.existsByEmail("test1@test.com");
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("전체 멤버를 조회할 수 있다.")
    void test_findAll() {
        // given
        memberRepository.save(member);
        memberRepository.save(librarian);

        // when
        List<Member> members = memberRepository.findAll();

        // then
        assertThat(members).hasSize(2);
    }

    @Test
    @DisplayName("고객 id로 조회할 수 있다")
    void test_findById() {
        Member saved = memberRepository.save(member);

        Optional<Member> found = memberRepository.findById(saved.getId());

        assertThat(found).isNotEmpty();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("고객 이름으로 조회할 수 있다")
    void test_findByName() {
        memberRepository.save(member);

        Optional<Member> found = memberRepository.findByName(member.getName());

        assertThat(found).isNotEmpty();
        assertThat(found.get().getName()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("멤버 이메일로 조회")
    void test_findByEmail() throws Exception {
        // given
        memberRepository.save(member);
        
        // when
        Optional<Member> found = memberRepository.findByEmail(member.getEmail());

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get().getEmail()).isEqualTo(member.getEmail());
    }


    @Test
    @DisplayName("멤버를 통해서 멤버의 비밀번호를 확인한다.")
    void test_password() {
        // given
        memberRepository.save(member);

        // when
        Optional<Member> found = memberRepository.findByName("customer1");

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get().getPassword()).isEqualTo("password");

    }

    private Member createMember() {
        return Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .memberRole(MemberRole.CUSTOMER)
                .build();
    }

    private Member createLibrarian() {
        return Member.builder()
                .name("librarian1")
                .email("librarian1@test.com")
                .password("password")
                .memberRole(MemberRole.LIBRARIAN)
                .build();
    }
}