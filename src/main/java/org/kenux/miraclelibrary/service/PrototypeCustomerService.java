package org.kenux.miraclelibrary.service;

import org.kenux.miraclelibrary.domain.Customer;
import org.kenux.miraclelibrary.repository.CustomerRepository;
import org.kenux.miraclelibrary.rest.dto.CustomerJoinDto;

public class PrototypeCustomerService implements CustomerService {

    private final CustomerRepository customerRepository;

    public PrototypeCustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer join(CustomerJoinDto customerJoinDto) {
        Customer customer = Customer.builder()
                .name(customerJoinDto.getName())
                .email(customerJoinDto.getEmail())
                .password(customerJoinDto.getPassword())
                .build();

        return customerRepository.save(customer);
    }
}