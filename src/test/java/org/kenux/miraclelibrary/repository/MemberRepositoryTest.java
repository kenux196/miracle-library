package org.kenux.miraclelibrary.repository;

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

    @Test
    @DisplayName("고객 정보 저장 테스트")
    void test_saveCustomer() {
        Member member = Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .memberRole(MemberRole.CUSTOMER)
                .build();;

        Member saved = memberRepository.save(member);

        assertThat(member.getId()).isNotNull();
        assertThat(saved.getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("고객 이메일이 존재하는지 검사한다.")
    void test_existEmail() {
        Member member = Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .memberRole(MemberRole.CUSTOMER)
                .build();;
        memberRepository.save(member);

        boolean result = memberRepository.existsByEmail("customer1@test.com");
        assertThat(result).isTrue();

        result = memberRepository.existsByEmail("test1@test.com");
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    void test_findAll() {
        Member member = Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .memberRole(MemberRole.CUSTOMER)
                .build();;
        memberRepository.save(member);

        member = Member.builder()
                .name("customer2")
                .email("customer2@test.com")
                .password("password")
                .memberRole(MemberRole.CUSTOMER)
                .build();;
        memberRepository.save(member);

        List<Member> members = memberRepository.findAll();

        assertThat(members).hasSize(2);
    }

    @Test
    @DisplayName("고객 id로 조회할 수 있다")
    void test_findById() {
        Member member = Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .memberRole(MemberRole.CUSTOMER)
                .build();;
        Member saved = memberRepository.save(member);

        Optional<Member> found = memberRepository.findById(saved.getId());

        assertThat(found).isNotEmpty();
        assertThat(found.get().getId()).isEqualTo(saved.getId());
    }

    @Test
    @DisplayName("고객 이름으로 조회할 수 있다")
    void test_findByName() {
        Member member = Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .memberRole(MemberRole.CUSTOMER)
                .build();;
        memberRepository.save(member);

        Optional<Member> found = memberRepository.findByName("customer1");

        assertThat(found).isNotEmpty();
        assertThat(found.get().getName()).isEqualTo("customer1");
    }

}