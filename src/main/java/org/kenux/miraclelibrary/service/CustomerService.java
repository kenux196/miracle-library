package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.MemberJoinDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.kenux.miraclelibrary.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final MemberRepository memberRepository;

    public Member join(MemberJoinDto memberJoinDto) {
        if (memberRepository.existsByEmail(memberJoinDto.getEmail())) {
            throw new CustomException(EMAIL_DUPLICATION);
        }

        if (memberJoinDto.getPassword().length() < 8) {
            throw new CustomException(PASSWORD_SHORT);
        }

        Member member = memberJoinDto.toEntity();
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