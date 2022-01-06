package org.kenux.miraclelibrary.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.config.JpaTestConfig;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.domain.enums.MemberRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(JpaTestConfig.class)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

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