package org.kenux.miraclelibrary.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.Member;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MemberMemoryRepositoryTest {

    CustomerMemoryRepository customerRepository = new CustomerMemoryRepository();

    @BeforeEach
    void init() {
        customerRepository.clear();
    }

    @Test
    @DisplayName("고객 정보 저장 테스트")
    void test_saveCustomer() {
        Member member = Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .build();

        Member savedMember = customerRepository.save(member);

        assertThat(savedMember.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("고객 이메일이 존재하는지 검사한다.")
    void test_existEmail() {
        Member member = Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .build();
        customerRepository.save(member);

        boolean result = customerRepository.existsByEmail("customer1@test.com");
        assertThat(result).isTrue();

        result = customerRepository.existsByEmail("customer11@test.com");
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    void test_findAll() {
        Member member = Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .build();

        customerRepository.save(member);

        member = Member.builder()
                .name("customer2")
                .email("customer2@test.com")
                .password("password")
                .build();
        customerRepository.save(member);

        List<Member> members = customerRepository.findAll();

        assertThat(members).hasSize(2);
    }

    @Test
    @DisplayName("고객 id로 조회할 수 있다")
    void test_findById() {
        Member member = Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .build();
        customerRepository.save(member);

        Optional<Member> found = customerRepository.findById(1L);

        assertThat(found).isNotEmpty();
        assertThat(found.get().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("고객 이름으로 조회할 수 있다")
    void test_findByName() {
        Member member = Member.builder()
                .name("customer1")
                .email("customer1@test.com")
                .password("password")
                .build();
        customerRepository.save(member);

        Optional<Member> found = customerRepository.findByName("customer1");

        assertThat(found).isNotEmpty();
        assertThat(found.get().getName()).isEqualTo("customer1");
    }
}