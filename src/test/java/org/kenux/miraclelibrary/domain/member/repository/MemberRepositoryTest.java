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
    @DisplayName("회원 저장: 기본 저장")
    void test_saveCustomer() {
        Member saved = memberRepository.save(member);

        assertThat(member.getId()).isNotNull();
        assertThat(saved.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("회원 저장: 생성일(가입일) 저장")
    void test_savedMemberHasCreateDate() {
        Member saved = memberRepository.save(member);

        assertThat(saved.getCreateDate()).isNotNull();
        assertThat(saved.getCreateDate()).isAfter(
                LocalDateTime.of(2022, 1, 1, 1,1,1));
    }
    
    @Test
    @DisplayName("회원 정보 변경: UpdateDate 갱신")
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
    @DisplayName("회원 이메일이 존재하는지 검사한다.")
    void test_existEmail() {
        memberRepository.save(member);

        boolean result = memberRepository.existsByEmail("customer1@test.com");
        assertThat(result).isTrue();

        result = memberRepository.existsByEmail("test1@test.com");
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("전체 멤버 조회")
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
    @DisplayName("회원 조회: id로 조회")
    void test_findById() {
        Member saved = memberRepository.save(member);

        Optional<Member> found = memberRepository.findById(saved.getId());

        assertThat(found).isNotEmpty();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("회원 조회: 이름으로 조회")
    void test_findByName() {
        memberRepository.save(member);

        Optional<Member> found = memberRepository.findByName(member.getName());

        assertThat(found).isNotEmpty();
        assertThat(found.get().getName()).isEqualTo(member.getName());
    }

    @Test
    @DisplayName("회원 조회: 이메일로 조회")
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
    @DisplayName("회원 비밀번호 조회")
    void getPassword() {
        // given
        memberRepository.save(member);

        // when
        Optional<Member> found = memberRepository.findByName("customer1");

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get().getPassword()).isEqualTo("password");
    }

    @Test
    @DisplayName("회원 주소 변경되어서 저장되는지 확인")
    void changeAndSaveAddressTest() throws Exception {
        // given
        String address = "대구시 달성군";
        member.changeAddress(address);

        // when
        final Member save = memberRepository.save(member);

        // then
        assertThat(save.getAddress()).isEqualTo(address);
    }

    @Test
    @DisplayName("회원 최종 접속 시간 저장")
    void updateLastAccessTime() throws Exception {
        // given
        LocalDateTime lastAccessTime = LocalDateTime.of(
                2022, 1, 22, 12, 12, 23);
        member.updateLastAccessTime(lastAccessTime);
        memberRepository.save(member);
        em.flush();
        em.clear();

        // when
        final Optional<Member> found = memberRepository.findById(member.getId());

        // then
        assertThat(found).isNotEmpty();
        assertThat(found.get().getLastAccessTime()).isEqualTo(lastAccessTime);
    }


    private Member createMember() {
        return Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .phone("010-1234-1234")
                .password("password")
                .memberRole(MemberRole.CUSTOMER)
                .build();
    }

    private Member createLibrarian() {
        return Member.builder()
                .name("librarian1")
                .email("librarian1@test.com")
                .phone("010-1234-1234")
                .password("password")
                .memberRole(MemberRole.LIBRARIAN)
                .build();
    }
}