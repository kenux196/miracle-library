package org.kenux.miraclelibrary.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.exception.CustomException;
import org.kenux.miraclelibrary.repository.MemberRepository;
import org.kenux.miraclelibrary.rest.dto.LibrarianAddDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.kenux.miraclelibrary.exception.ErrorCode.EMAIL_DUPLICATION;

@Service
@RequiredArgsConstructor
public class LibrarianManagementService {

    private final MemberRepository memberRepository;

    public List<Member> getAllCustomer() {
        return memberRepository.findAll();
    }

    public Optional<Member> getCustomerByName(String name) {
        return memberRepository.findByName(name);
    }

    public Optional<Member> getCustomer(Long id) {
        return memberRepository.findById(id);
    }

    public Member addLibrarian(LibrarianAddDto librarianAddDto) {
        if (memberRepository.existsByEmail(librarianAddDto.getEmail())) {
            throw new CustomException(EMAIL_DUPLICATION);
        }

        Member member = librarianAddDto.toEntity();
        return memberRepository.save(member);
    }
}