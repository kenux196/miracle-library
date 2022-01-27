package org.kenux.miraclelibrary.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.member.dto.MemberJoinRequest;
import org.kenux.miraclelibrary.domain.member.service.MemberJoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberJoinService memberJoinService;

    @PostMapping
    public ResponseEntity<?> joinMember(@Valid @RequestBody MemberJoinRequest memberJoinRequest) {
        final Long newMemberId = memberJoinService.join(memberJoinRequest);
        return ResponseEntity.ok(newMemberId);
    }
}