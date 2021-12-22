package org.kenux.miraclelibrary.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.Customer;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class CustomerMemoryRepositoryTest {

    CustomerRepository customerRepository = new CustomerMemoryRepository();

    @BeforeEach
    void init() {
        customerRepository.clear();
    }

    @Test
    @DisplayName("고객 정보 저장 테스트")
    void test_saveCustomer() {
        Customer customer = new Customer("name", "test@test.com", "password");

        Customer savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("고객 이메일이 존재하는지 검사한다.")
    void test_existEmail() {
        Customer customer = new Customer("name", "test@test.com", "password");
        Customer savedCustomer = customerRepository.save(customer);

        boolean result = customerRepository.existsByEmail("test@test.com");
        assertThat(result).isTrue();

        result = customerRepository.existsByEmail("test1@test.com");
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("전체 고객을 조회할 수 있다.")
    void test_findAll() {
        Customer customer = new Customer("customer1", "customer1@test.com", "password");
        customerRepository.save(customer);

        customer = new Customer("customer2", "customer2@test.com", "password");
        customerRepository.save(customer);

        List<Customer> customers = customerRepository.findAll();

        assertThat(customers).hasSize(2);
    }

    @Test
    @DisplayName("고객 id로 조회할 수 있다")
    void test_findById() {
        Customer customer = new Customer("customer1", "customer1@test.com", "password");
        customerRepository.save(customer);

        Optional<Customer> found = customerRepository.findById(1L);

        assertThat(found).isNotEmpty();
        assertThat(found.get().getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("고객 이름으로 조회할 수 있다")
    void test_findByName() {
        Customer customer = new Customer("customer1", "customer1@test.com", "password");
        customerRepository.save(customer);

        Optional<Customer> found = customerRepository.findByName("customer1");

        assertThat(found).isNotEmpty();
        assertThat(found.get().getName()).isEqualTo("customer1");
    }
}