package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.exception.ErrorCode;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.LoginRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    public Member login(LoginRequest loginRequest) throws Exception {
        final Member member = memberRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        if (!loginRequest.getPassword().equals(member.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_WRONG);
        }
        return member;
    }
}