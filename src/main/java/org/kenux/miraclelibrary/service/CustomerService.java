package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.MemberJoinRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.kenux.miraclelibrary.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final MemberRepository memberRepository;

    public Member join(MemberJoinRequestDto memberJoinRequestDto) {
        if (memberRepository.existsByEmail(memberJoinRequestDto.getEmail())) {
            throw new CustomException(EMAIL_DUPLICATION);
        }

        if (memberJoinRequestDto.getPassword().length() < 8) {
            throw new CustomException(PASSWORD_SHORT);
        }

        Member member = memberJoinRequestDto.toEntity();
        return memberRepository.save(member);
    }

    public List<Member> getAllCustomer() {
        return memberRepository.findAll();
    }

    public Optional<Member> getCustomerByName(String name) {
        return memberRepository.findByName(name);
    }

    public Optional<Member> getCustomer(Long id) {
        return memberRepository.findById(id);
    }
}