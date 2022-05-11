package org.kenux.miraclelibrary.web.member.controller;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.member.domain.Member;
import org.kenux.miraclelibrary.web.member.dto.LoginRequest;
import org.kenux.miraclelibrary.web.member.dto.LoginResponse;
import org.kenux.miraclelibrary.domain.member.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) throws Exception {
        final Member member = loginService.login(loginRequest);
        return ResponseEntity.ok(LoginResponse.from(member));
    }
}