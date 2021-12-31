package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.LibrarianAddRequest;
import org.springframework.stereotype.Service;

import static org.kenux.miraclelibrary.exception.ErrorCode.EMAIL_DUPLICATION;

@Service
@RequiredArgsConstructor
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