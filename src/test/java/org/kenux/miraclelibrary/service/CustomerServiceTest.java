package org.kenux.miraclelibrary.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kenux.miraclelibrary.domain.Customer;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.exception.ErrorCode;
import org.kenux.miraclelibrary.repository.CustomerRepository;
import org.kenux.miraclelibrary.rest.dto.CustomerJoinDto;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.kenux.miraclelibrary.exception.ErrorCode.EMAIL_DUPLICATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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
    @DisplayName("회원 가입시 이메일 중복인 경우, exception 발생")
    void test_duplicateEmail() {
        // given
        CustomerJoinDto customerJoinDto = new CustomerJoinDto("name", "name@email.com", "password");
        given(customerRepository.existsByEmail(any())).willReturn(true);

        // when then
        assertThatThrownBy(() -> customerService.join(customerJoinDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(EMAIL_DUPLICATION.getMessage());
    }

    @Test
    @DisplayName("회원 패스워드 검증 - 길이가 8자보다 작으면 예외 발생")
    void test_validatePassword() {
        // given
        CustomerJoinDto customerJoinDto = new CustomerJoinDto("name", "name@email.com", "pass");

        // when then
        assertThatThrownBy(() -> customerService.join(customerJoinDto))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.PASSWORD_SHORT.getMessage());
    }

    @Test
    @DisplayName("가입된 회원 전체를 조회할 수 있어야 한다.")
    void test_findAllCustomer() {
        // given
        List<Customer> customers = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String name = "customer" + 1;
            String email = name +"@email.com";
            String password = "password";
            customers.add(new Customer(name, email, password));
        }
        Customer customer = new Customer("name", "name@email.com", "password");
        customer.assignId(1L);
        when(customerRepository.findAll()).thenReturn(customers);

        // when
        List<Customer> customerList = customerService.getAllCustomer();

        // then
        assertThat(customerList).hasSize(100);
    }

    @Test
    @DisplayName("회원 이름으로 고객 조회가 가능해야 한다.")
    void test_findByCustomerName() {
        // given
        Customer customer = new Customer("name", "name@email.com", "password");
        customer.assignId(1L);
        when(customerRepository.findByName(any())).thenReturn(Optional.of(customer));

        // when
        Optional<Customer> result = customerService.getCustomerByName("name");

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getName()).isEqualTo("name");
    }

    @Test
    @DisplayName("회원 아이디를 통한 회원 조회가 가능하다.")
    void test_findByCustomerId() {
        // given
        Customer customer = new Customer("name", "name@email.com", "password");
        customer.assignId(1L);
        when(customerRepository.findById(any())).thenReturn(Optional.of(customer));

        // when
        Optional<Customer> result = customerService.getCustomer(1L);

        // then
        assertThat(result).isNotEmpty();
        assertThat(result.get().getId()).isEqualTo(1L);
    }


}