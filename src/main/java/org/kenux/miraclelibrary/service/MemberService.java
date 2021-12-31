package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.MemberJoinRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.kenux.miraclelibrary.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long join(MemberJoinRequest memberJoinRequest) {
        validateEmail(memberJoinRequest.getEmail());
        validatePassword(memberJoinRequest.getPassword());
        Member member = memberJoinRequest.toEntity();
        return memberRepository.save(member).getId();
    }

    private void validateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new CustomException(EMAIL_DUPLICATION);
        }
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new CustomException(PASSWORD_SHORT);
        }
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