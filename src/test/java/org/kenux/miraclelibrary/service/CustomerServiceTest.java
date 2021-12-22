package org.kenux.miraclelibrary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.Customer;
import org.kenux.miraclelibrary.repository.CustomerMemoryRepository;
import org.kenux.miraclelibrary.repository.CustomerRepository;
import org.kenux.miraclelibrary.rest.dto.CustomerJoinDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    @DisplayName("회원 가입을 한다.")
    void test_joinCustomer() {
        // given
        Customer customer = new Customer("name", "name@email.com", "password");
        customer.assignId(1L);
        when(customerRepository.save(any())).thenReturn(customer);

        // when
        CustomerJoinDto customerJoinDto = new CustomerJoinDto("name", "name@email.com", "password");
        Customer saved = customerService.join(customerJoinDto);

        // then
        assertThat(saved.getId()).isEqualTo(1);
    }

    @Test
    @DisplayName("회원 정보 변경 기능 제공한다.")
    void test_modifyCustomerInfo() {
        // given
        CustomerJoinDto customerJoinDto = new CustomerJoinDto("name", "name@email.com", "password");

    }
}