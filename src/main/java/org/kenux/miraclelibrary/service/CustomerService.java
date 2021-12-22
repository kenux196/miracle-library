package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Customer;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.exception.ErrorCode;
import org.kenux.miraclelibrary.repository.CustomerRepository;
import org.kenux.miraclelibrary.rest.dto.CustomerJoinDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.kenux.miraclelibrary.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer join(CustomerJoinDto customerJoinDto) {
        if (customerRepository.existsByEmail(customerJoinDto.getEmail())) {
            throw new CustomException(EMAIL_DUPLICATION);
        }

        Customer customer = Customer.builder()
                .name(customerJoinDto.getName())
                .email(customerJoinDto.getEmail())
                .password(customerJoinDto.getPassword())
                .build();

        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    public Optional<Customer> getCustomer(Long id) {
        return customerRepository.findById(id);
    }
}