package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Customer;
import org.kenux.miraclelibrary.repository.CustomerRepository;
import org.kenux.miraclelibrary.rest.dto.CustomerJoinDto;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public Customer join(CustomerJoinDto customerJoinDto) {
        Customer customer = Customer.builder()
                .name(customerJoinDto.getName())
                .email(customerJoinDto.getEmail())
                .password(customerJoinDto.getPassword())
                .build();

        return customerRepository.save(customer);
    }
}