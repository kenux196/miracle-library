package org.kenux.miraclelibrary.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.dto.LibrarianAddRequest;
import org.kenux.miraclelibrary.domain.member.repository.MemberRepository;
import org.kenux.miraclelibrary.global.exception.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.kenux.miraclelibrary.global.exception.ErrorCode.EMAIL_DUPLICATION;

@Service
@RequiredArgsConstructor
@Transactional
public class LibrarianManagementService {

    private final MemberRepository memberRepository;

    public Member addLibrarian(LibrarianAddRequest librarianAddRequest) {
        if (memberRepository.existsByEmail(librarianAddRequest.getEmail())) {
            throw new CustomException(EMAIL_DUPLICATION);
        }

        Member member = librarianAddRequest.toEntity();
        return memberRepository.save(member);
    }
}