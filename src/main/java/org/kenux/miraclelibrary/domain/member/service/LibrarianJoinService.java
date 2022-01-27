package org.kenux.miraclelibrary.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.dto.LibrarianJoinRequest;
import org.kenux.miraclelibrary.domain.member.repository.MemberRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.kenux.miraclelibrary.global.exception.ErrorCode.EMAIL_DUPLICATION;

@Service
@RequiredArgsConstructor
@Transactional
public class LibrarianJoinService {

    private final MemberRepository memberRepository;

    public Long add(LibrarianJoinRequest librarianJoinRequest) {
        if (memberRepository.existsByEmail(librarianJoinRequest.getEmail())) {
            throw new CustomException(EMAIL_DUPLICATION);
        }

        Member member = librarianJoinRequest.toEntity();
        return memberRepository.save(member).getId();
    }
}