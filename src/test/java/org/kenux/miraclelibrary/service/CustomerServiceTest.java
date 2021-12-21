package org.kenux.miraclelibrary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.kenux.miraclelibrary.domain.Customer;
import org.kenux.miraclelibrary.repository.CustomerMemoryRepository;
import org.kenux.miraclelibrary.rest.dto.CustomerJoinDto;

import static org.assertj.core.api.Assertions.assertThat;

// 메모리 Repository 이므로 Mocking 하지 않을 것임.
class CustomerServiceTest {

    private final CustomerService customerService;

    public CustomerServiceTest() {
        customerService = new PrototypeCustomerService(new CustomerMemoryRepository());
    }

    @Test
    @DisplayName("회원 가입을 한다.")
    void test_joinCustomer() {
        // given
        CustomerJoinDto customerJoinDto = new CustomerJoinDto("name", "name@email.com", "password");

        // when
        Customer saved = customerService.join(customerJoinDto);

        // then
        assertThat(saved.getId()).isEqualTo(1);
    }

}