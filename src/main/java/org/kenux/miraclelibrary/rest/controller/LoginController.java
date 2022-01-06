package org.kenux.miraclelibrary.rest.controller;

import lombok.RequiredArgsConstructor;
import org.kenux.miraclelibrary.domain.Member;
import org.kenux.miraclelibrary.rest.dto.LoginRequest;
import org.kenux.miraclelibrary.rest.dto.LoginResponse;
import org.kenux.miraclelibrary.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest loginRequest) {
        final Member member = loginService.login(loginRequest);
        return ResponseEntity.ok(LoginResponse.of(member));
    }
}