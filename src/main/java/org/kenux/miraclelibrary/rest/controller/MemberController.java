package org.kenux.miraclelibrary.rest.controller;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.rest.dto.MemberJoinRequest;
import org.kenux.miraclelibrary.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> joinMember(@Valid @RequestBody MemberJoinRequest memberJoinRequest) {
        final Long newMemberId = memberService.join(memberJoinRequest);
        return ResponseEntity.ok(newMemberId);
    }
}