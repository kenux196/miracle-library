package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.exception.CustomException;
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

    public Member join(CustomerJoinDto customerJoinDto) {
        if (customerRepository.existsByEmail(customerJoinDto.getEmail())) {
            throw new CustomException(EMAIL_DUPLICATION);
        }

        if (customerJoinDto.getPassword().length() < 8) {
            throw new CustomException(PASSWORD_SHORT);
        }

        Member member = Member.builder()
                .name(customerJoinDto.getName())
                .email(customerJoinDto.getEmail())
                .password(customerJoinDto.getPassword())
                .build();

        return customerRepository.save(member);
    }

    public List<Member> getAllCustomer() {
        return customerRepository.findAll();
    }

    public Optional<Member> getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    public Optional<Member> getCustomer(Long id) {
        return customerRepository.findById(id);
    }
}