package org.kenux.miraclelibrary.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.domain.member.repository.MemberRepository;
import org.kenux.miraclelibrary.web.member.dto.response.MemberBasicInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberFindService {

    private final MemberRepository memberRepository;

    public List<MemberBasicInfoResponse> getMembers() {
        return memberRepository.findAll().stream()
                .map(MemberBasicInfoResponse::from)
                .collect(Collectors.toList());
    }

    public Optional<Member> getMembersByName(String name) {
        return memberRepository.findByName(name);
    }

    public Optional<Member> getMember(Long id) {
        return memberRepository.findById(id);
    }
}