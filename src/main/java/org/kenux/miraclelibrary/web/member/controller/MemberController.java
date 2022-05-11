package org.kenux.miraclelibrary.web.member.controller;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.web.member.dto.MemberJoinRequest;
import org.kenux.miraclelibrary.domain.member.service.MemberFindService;
import org.kenux.miraclelibrary.domain.member.service.MemberJoinService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/members", produces = {MediaType.APPLICATION_JSON_VALUE})
@RequiredArgsConstructor
public class MemberController {

    private final MemberJoinService memberJoinService;
    private final MemberFindService memberFindService;

    @PostMapping
    public ResponseEntity<?> joinMember(@Valid @RequestBody MemberJoinRequest memberJoinRequest) {
        final Long newMemberId = memberJoinService.join(memberJoinRequest);
        return ResponseEntity.ok(newMemberId);
    }

    @GetMapping
    public ResponseEntity<?> getAllMember() {
        return ResponseEntity.ok(memberFindService.getMembers());
    }
}