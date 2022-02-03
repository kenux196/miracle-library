package org.kenux.miraclelibrary.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.dto.MemberJoinRequest;
import org.kenux.miraclelibrary.domain.member.repository.MemberRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.kenux.miraclelibrary.global.exception.ErrorCode.EMAIL_DUPLICATION;
import static org.kenux.miraclelibrary.global.exception.ErrorCode.PASSWORD_SHORT;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberJoinServiceImpl implements MemberJoinService {

    private final MemberRepository memberRepository;

    @Override
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
}